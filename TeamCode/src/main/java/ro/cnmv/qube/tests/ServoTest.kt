package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import ro.cnmv.qube.core.RobotOpMode

@TeleOp (name = "Servo Test", group = "Tests")
class ServoTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()

        val controller = robot.jewServo.controller
        val servo = 1

        fun getPosition() = controller.getServoPosition(servo)

        val position = getPosition()

        fun setPosition(position: Double) = controller.setServoPosition(servo, position)

        while (opModeActive) {
            setPosition(position)
        }
    }

}
