package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.*
import ro.cnmv.qube.core.OpModeAccess

interface CubesDrop: OpModeAccess{
    val leftDropServo: Servo
    val rightDropServo: Servo
    var dropPosition: Double

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
        if (gp.right_trigger > 0.7) {
            dropPosition = Math.min(1.0, dropPosition + 0.1)
        } else if (gp.left_trigger > 0.7) {
            dropPosition = Math.max(0.0, dropPosition - 0.1)
        } else if (gp.right_bumper) {
            dropPosition = 0.33
        }

        tele.addData("Drop Position", dropPosition)

        leftDropServo.position = dropPosition
        rightDropServo.position = dropPosition
    }
}
