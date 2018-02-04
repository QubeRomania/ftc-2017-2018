package ro.cnmv.qube.tests

import ro.cnmv.qube.core.RobotOpMode

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
