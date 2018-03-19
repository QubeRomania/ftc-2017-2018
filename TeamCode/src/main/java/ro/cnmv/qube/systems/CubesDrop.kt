package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.CRServoImpl
import com.qualcomm.robotcore.hardware.CRServoImplEx
import com.qualcomm.robotcore.hardware.Gamepad

interface CubesDrop {
    val leftDropServo: CRServo
    val rightDropServo: CRServo

    fun dropWithGamepad(gp: Gamepad) {
        val power =  gp.left_trigger - gp.right_trigger
        drop(power.toDouble())
    }

    fun drop(power: Double) {
        leftDropServo.power = power
        rightDropServo.power = power
    }
}
