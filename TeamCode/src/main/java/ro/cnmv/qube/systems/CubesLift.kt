package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.Gamepad

interface CubesLift {
    val leftLiftServo: CRServo
    val rightLiftServo: CRServo

    var power: Double

    fun liftWithGamepad(gp: Gamepad) {
        power = when {
            gp.a -> 1.0
            gp.b -> -1.0
            gp.x -> 0.0
            else -> power
        }

        lift(power)
    }

    fun lift(power: Double) {
        leftLiftServo.power = power
        rightLiftServo.power = power
    }
}
