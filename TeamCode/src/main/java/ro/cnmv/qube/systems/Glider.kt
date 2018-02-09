package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad

interface Glider {
    val gliderMotor: DcMotor
    fun gliderWithGamepad(gp: Gamepad){
        val power = gp.right_trigger - gp.left_trigger
        gliderMotor.power = power.toDouble()
    }
}
