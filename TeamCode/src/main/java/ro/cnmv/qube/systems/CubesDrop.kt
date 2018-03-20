package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.*

interface CubesDrop {
    val leftDropServo: Servo
    val rightDropServo: Servo

    fun dropCubesAuto(extended: Boolean) {
        if (extended) {
            leftDropServo.position = 1.0
            rightDropServo.position = 1.0
        } else {
            leftDropServo.position = 0.0
            rightDropServo.position = 0.0
        }
    }

    fun dropWithGamepad(gp: Gamepad) {
        var position = leftDropServo.position

        if (gp.right_trigger > 0.2 && position <= 0.9) {
            position += 0.1
        } else if (gp.left_trigger > 0.2 && position >= 0.1) {
            position -= 0.1
        } else if (gp.right_bumper) {
            position = 0.2
        }

        leftDropServo.position = position
        rightDropServo.position = position
    }
}
