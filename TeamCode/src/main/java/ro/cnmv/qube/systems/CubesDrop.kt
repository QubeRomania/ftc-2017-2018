package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.*

interface CubesDrop {
    val leftDropServo: Servo
    val rightDropServo: Servo

    fun dropWithGamepad(gp: Gamepad) {
        var power = gp.left_trigger - gp.right_trigger
        drop(power.toDouble())
    }

    fun drop(power: Double) {
        if(power < 0.2 && power > -0.2) return
        leftDropServo.position += power / 50.0
        rightDropServo.position += power / 50.0
    }
}
