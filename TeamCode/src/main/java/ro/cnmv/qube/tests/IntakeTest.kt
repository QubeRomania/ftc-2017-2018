package ro.cnmv.qube.tests

import ro.cnmv.qube.core.RobotOpMode
import ro.cnmv.qube.pid.RemotePid

class IntakeTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()

        while (opModeIsActive()) {
            robot.intakeWithGamepad(gamepad2)
        }
    }
}
