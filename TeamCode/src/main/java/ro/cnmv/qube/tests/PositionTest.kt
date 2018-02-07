package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Encoder Position Test", group = "Tests")
class PositionTest: RobotOpMode() {
    override fun runOpMode() {
        robot.resetEncoders()

        waitForStart()

        while (opModeIsActive()) {
            robot.printPositions()
            update()
        }
    }
}
