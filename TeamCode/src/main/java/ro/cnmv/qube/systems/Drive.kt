package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import ro.cnmv.qube.core.DriveMotors
import ro.cnmv.qube.core.Gyro
import ro.cnmv.qube.core.OpModeAccess
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sign

interface Drive: DriveMotors, Gyro, Sensors, OpModeAccess {
    companion object {
        private const val TICKS_PER_MOTOR_ROTATION = 1120.0
        private const val RATIO = 2.0 * TICKS_PER_MOTOR_ROTATION / 65.0

        private const val VOLTAGE_THRESHOLD = 1.5

        val Double.ticks
            get() = (this * RATIO).roundToInt()
    }

    fun driveWithGamepad(gp: Gamepad) {
        val x = -gp.left_stick_x.toDouble()
        val y = -gp.left_stick_y.toDouble()
        val z = -gp.right_stick_x.toDouble()

        setPower(
        y - x - z,
        y + x + z,
        y + x - z,
        y - x + z
        )
    }

    fun driveDistance(distance: Double, targetDirection: Double) {
        val pid = PIDCoefficients(0.1, 0.02, 0.1)
        driveDistanceWithPid(distance, targetDirection, pid)
    }

    fun driveDistanceWithPid(distance: Double, targetDirection: Double, pid: PIDCoefficients) {
        val p = pid.p
        val i = pid.i
        val d = pid.d

        // Stop and reset the encoders.
        resetEncoders()

        // Convert distance in cm to motor ticks.
        val distanceTicks = distance.ticks.absoluteValue
        val sgn = distance.sign

        frontLeft.mode = DcMotor.RunMode.RUN_USING_ENCODER
        frontRight.mode = DcMotor.RunMode.RUN_USING_ENCODER


        // PID distance function.
        fun pidDrive(maxSpeed: Double, minDistance: Double) {
            var lastError = 0.0

            while (opModeActive && distanceTicks - frontLeft.currentPosition.absoluteValue > minDistance.ticks) {
                val error = (targetDirection - heading) * -1.0
                tele.addData("Error", error)

                val motorDif = ((p * error) + (i * (error + lastError) + (d * (error - lastError))))

                val sum0 = (sgn + motorDif) * maxSpeed
                val sum1 = (sgn - motorDif) * maxSpeed

                tele.update()

                setPower(sum0, sum1, sum0, sum1)

                lastError = error
            }
        }

        // Go fast until the last 30 cm.
        pidDrive(0.5, 30.0)

        // Precise PID.
        pidDrive(0.25, 0.0)

        stopMotors()
    }

    fun rotateTo(target: Double) {
        rotateToPower(target, 0.2)
        rotateToPower(target, 0.2)
    }

    fun rotateToPower(target: Double, rotatePower: Double) {
        do {
            val error = target - heading

            tele.addData("Current heading", heading)
            tele.addData("Target heading", target)
            tele.addData("Rotate Error", target - heading)
            tele.update()

            val power = rotatePower * error.sign

            setPower(-power, power, -power, power)

        } while (opModeActive && error.absoluteValue > 0.5)

        stopMotors()
    }

    fun driveTime(millis: Long, power: Double) {
        val timer = ElapsedTime()

        setPower(power, power, power, power)

        while (opModeActive && timer.milliseconds() < millis)
            ;

        stopMotors()
    }

    fun runToColumn(column: Int, directionSign: Double, targetHeading: Double) {
        val basePower = 0.40 * directionSign
        var lastAngleError = 0.0

        var leftCount = 0

        val timer = ElapsedTime()
        while (opModeActive && leftCount < column) {
            if (leftAnalogSensor.voltage > VOLTAGE_THRESHOLD && timer.milliseconds() > 1000) {
                ++leftCount
                timer.reset()
            }

            val motorCorrection = {
                val error = targetHeading - heading.toDouble()

                val P = 0.03
                val I = 0.00
                val D = 0.1
                val pid = P * error + I * (error + lastAngleError) + D * (error - lastAngleError)

                lastAngleError = error

                pid
            }()

            setPower(
                    -basePower - motorCorrection,
                    basePower + motorCorrection,
                    basePower - motorCorrection,
                    -basePower + motorCorrection)

            tele.addData("Target Column", column)
            tele.addData("Angle", heading)
            tele.addData("Correction", motorCorrection)
            tele.addData("LeftCount", leftCount)
            tele.addData("LeftVoltage", leftAnalogSensor.voltage)

            tele.update()
        }

        stopMotors()
    }
}
