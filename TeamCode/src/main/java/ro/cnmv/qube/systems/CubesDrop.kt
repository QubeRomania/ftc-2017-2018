package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.Gamepad

interface CubesDrop {
    val leftDropServo: CRServo
    val rightDropServo: CRServo

    fun dropWithGamepad(gp: Gamepad) {
        val power = gp.right_trigger - gp.left_trigger
        drop(power.toDouble())
    }

    fun drop(power: Double) {
        leftDropServo.power = power
        rightDropServo.power = power
    }
}
