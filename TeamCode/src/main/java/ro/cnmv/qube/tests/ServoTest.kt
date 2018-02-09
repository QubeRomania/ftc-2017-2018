package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous (name = "Servo Test", group = "Tests")
class ServoTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()
        while (opModeActive){
            robot.jewServo.position = -gamepad1.left_stick_y.toDouble()
        }
    }

}
