package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.Gamepad
import ro.cnmv.qube.core.DriveMotors
import ro.cnmv.qube.core.Gyro
import ro.cnmv.qube.pid.DistancePid
import ro.cnmv.qube.pid.Pid
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sign

interface Drive: DriveMotors, Gyro {
    companion object {
        private const val TICKS_PER_MOTOR_ROTATION = 1120.0
        private const val RATIO = 2.0 * TICKS_PER_MOTOR_ROTATION / 100.0

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

    fun driveDistance(distance: Double) {
        val pid = DistancePid()
        driveDistanceWithPid(distance, pid)
    }

    fun driveDistanceWithPid(distance: Double, pid: Pid) {
        val p = pid.p
        val i = pid.i
        val d = pid.d

        // Stop and reset the encoders.
        resetEncoders()

        // Convert distance in cm to motor ticks.
        val distanceTicks = distance.ticks.absoluteValue
        val sgn = distance.sign

        val targetDirection = heading

        // PID distance function.
        fun pid(maxSpeed: Double, minDistance: Double) {
            var lastError = 0.0

            while (distanceTicks - frontLeft.currentPosition.absoluteValue > minDistance.ticks) {
                val error = (heading - targetDirection) / 180.0

                val motorDif = (p * error) + (i * (error + lastError) + (d * (error - lastError)))

                val sum0 = (sgn + motorDif) * maxSpeed
                val sum1 = (sgn - motorDif) * maxSpeed

                setPower(sum0, sum1, sum0, sum1)

                lastError = error
            }
        }

        // Go fast until the last 60 cm.
        pid(0.7, 60.0)

        // Precise PID.
        pid(0.3, 0.0)

        stopMotors()
    }

    fun rotateTo(target: Double) {
        val delta = target - heading
        val sgn = delta.sign

        while (Math.abs(target - heading) > 5.0) {
            val power = 0.2 * sgn
            setPower(-power, power, -power, power)
        }

        stopMotors()
    }
}
