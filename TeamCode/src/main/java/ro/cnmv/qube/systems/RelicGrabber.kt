package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime

interface RelicGrabber{
    val relicGrabServo: Servo
    val relicLiftServo: Servo

    var relicGrabTime: Long
    var relicLiftTime: Long

    var relicGrabState: Boolean
    var relicLiftState: Boolean

    val timer: ElapsedTime

    fun grabRelic(gp: Gamepad){
        if(gp.left_bumper && timer.milliseconds() - relicGrabTime > 1000){
            relicGrabState = !relicGrabState
            relicGrabTime = timer.milliseconds().toLong()
        }

        relicGrabServo.position = when(relicGrabState) {
            true -> 1.0
            else -> 0.0
        }
    }

    fun liftRelic(gp: Gamepad){
        if(gp.right_bumper && timer.milliseconds() - relicLiftTime > 1000){
            relicLiftState = !relicLiftState
            relicLiftTime = timer.milliseconds().toLong()
        }

        relicLiftServo.position = when(relicLiftState){true -> 1.0 else -> 0.0}
    }
}
