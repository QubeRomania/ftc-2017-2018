package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Gyro Test", group = "Tests")
class GyroTest: RobotOpMode() {
    override fun runOpMode() {
        calibrateGyro()

        waitForStart()

        while (opModeIsActive()) {
            telemetry.addData("Heading", robot.heading)
            update()

            sleep(50)
        }
    }
}
