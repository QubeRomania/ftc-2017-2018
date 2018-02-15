package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime

interface Glider {
    val gliderMotor: DcMotor
    val gliderLockServo: Servo
    
    val timer: ElapsedTime
    var gliderLockTime: Long
    var gliderLockState: Boolean
    fun gliderWithGamepad(gp: Gamepad){
        val power = gp.right_trigger - gp.left_trigger
        gliderMotor.power = power.toDouble()
    }
    
    fun unlockGlider(gp: Gamepad){
        if(gp.b && timer.milliseconds() - gliderLockTime > 1000){
            gliderLockState = !gliderLockState
            gliderLockTime = timer.milliseconds().toLong()
        }
        gliderLockServo.position = when(gliderLockState) {
            true -> 0.0
            else -> 0.5
        }
    }
}
