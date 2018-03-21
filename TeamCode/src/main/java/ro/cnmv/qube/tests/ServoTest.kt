package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import ro.cnmv.qube.core.RobotOpMode

@TeleOp (name = "Servo Test", group = "Tests")
class ServoTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()

        var position = 0.0

        robot.leftDropServo.direction = Servo.Direction.REVERSE

        while(opModeIsActive()){
            robot.leftDropServo.position = position
            waitForMs(1000)
            position = position + 0.1
            if(position > 1) position = 0.0
            telemetry.addData("postion", position)
            telemetry.update()
        }
    }
}
