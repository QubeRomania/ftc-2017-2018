package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import com.qualcomm.robotcore.util.Range
import ro.cnmv.qube.core.Gamepad
import ro.cnmv.qube.core.GamepadButton
import ro.cnmv.qube.core.RobotOpMode
import kotlin.math.absoluteValue
import kotlin.math.sign

@Autonomous(name = "PID Test", group = "Tests")
class PidTest: RobotOpMode() {
    override fun runOpMode() {
        calibrateGyro()

        //val pid = RemotePid()
        //pid.beginListening()

        waitForStart()

        val gp = Gamepad(gamepad1)

        var targetAngle = 0.0
        var lastStates = arrayOf(false, false, false, false)

        while (opModeIsActive()) {
            if (lastStates[0] != gp.checkButtonToggle(GamepadButton.A)) {
                targetAngle += 5.0
                lastStates[0] = !lastStates[0]
            }

            if (lastStates[1] != gp.checkButtonToggle(GamepadButton.X)) {
                targetAngle -= 5.0
                lastStates[1] = !lastStates[1]
            }

            if (lastStates[2] != gp.checkButtonToggle(GamepadButton.B)) {
                robot.rotateTo(targetAngle)
                lastStates[2] = !lastStates[2]
            }

            if (gp.checkButtonToggle(GamepadButton.Y)) {
                val basePower = 1.0
                val pid = PIDCoefficients(0.6, 0.5, 0.1)

                var error = 0.0
                var lastError: Double

                val safetyTimer = ElapsedTime()

                do {
                    val currentHeading = robot.heading

                    lastError = error
                    error = (currentHeading - targetAngle) / 90.0

                    val scale = pid.p + pid.i + pid.d

                    var steeringCorrection = Range.clip(
                            (error * pid.p + ((error + lastError) * pid.i) + ((error - lastError) * pid.d)) / scale,
                            -1.0, 1.0
                    )

                    if (steeringCorrection.absoluteValue < 0.1)
                        steeringCorrection = 0.1 * steeringCorrection.sign

                    val leftPower = Range.clip(basePower * steeringCorrection, -1.0, 1.0)
                    val rightPower = Range.clip(basePower * -steeringCorrection, -1.0, 1.0)

                    robot.setPower(leftPower, rightPower, leftPower, rightPower)

                    telemetry.addData("Error", error)
                    telemetry.addData("Correction", steeringCorrection)
                    telemetry.update()
                } while (opModeIsActive() && safetyTimer.seconds() < 4)

                telemetry.clear()

                robot.stopMotors()

                lastStates[3] = !lastStates[3]
            }

            tele.addData("Current heading", robot.heading)
            tele.addData("Target heading", targetAngle)
            telemetry.update()
        }

        //pid.shutdown()
        robot.stop()
    }
}
