package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import ro.cnmv.qube.core.RobotOpMode

@TeleOp (name = "Servo Test", group = "Tests")
class ServoTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()
        while (opModeActive){
            robot.intakeWithGamepad(gamepad2)
            robot.jewServo.position = (-gamepad1.left_stick_y.toDouble() + 1) / 2
        }
    }

}
