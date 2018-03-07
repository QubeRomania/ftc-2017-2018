package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Auto Strafing Test", group = "Test")
class AutoStrafingTest: RobotOpMode() {
    val targetDistance = 20.0


    override fun runOpMode() {
        calibrateGyro()
        robot.relicLiftServo.position = 0.0

        waitForStart()

        var lastAngleError = 0.0
        var lastDistanceError = 0.0
        while (opModeIsActive()){
            val basePower = when {
                gamepad1.x -> 1.0
                gamepad1.b -> -1.0
                else -> 0.0
            }

            val motorCorrection = {
                val error = -robot.heading.toDouble()

                val P = 0.05
                val I = 0.01
                val D = 0.2
                val pid = P * error + I * (error + lastAngleError) + D * (error - lastAngleError)

                lastAngleError = error

                pid
            }()

            val correction = {
                val currentDistance = robot.backDistance
                val error = targetDistance - currentDistance

                val P = 0.025
                val I = 0.005
                val D = 0.02

                val pid = P * error + I * (error + lastDistanceError) + D * (error - lastDistanceError)

                lastDistanceError = error

                pid
            }()
            robot.setPower(
                    -basePower - motorCorrection + correction,
                    basePower + motorCorrection + correction,
                    basePower - motorCorrection + correction,
                    -basePower + motorCorrection + correction)



            telemetry.addData("Angle", robot.heading)
            telemetry.addData("Correction", motorCorrection)
            telemetry.update()
        }
    }
}
