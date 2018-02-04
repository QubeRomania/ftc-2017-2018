package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Encoder Position Test", group = "Tests")
class PositionTest: RobotOpMode() {
    override fun runOpMode() {
        robot.setEncoders(true)

        waitForStart()

        while (opModeIsActive()) {
            robot.printPositions(telemetry)
            telemetry.update()
        }
    }
}
