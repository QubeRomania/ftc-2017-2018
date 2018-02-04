package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import ro.cnmv.qube.core.RobotOpMode

@TeleOp(name = "Intake Test", group = "Tests")
class IntakeTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()

        while (opModeIsActive()) {
            robot.intakeWithGamepad(gamepad2)
        }
    }
}
