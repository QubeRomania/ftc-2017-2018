package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad

interface CubesLift {
    val liftMotor: DcMotor

    var power: Double

    fun liftWithGamepad(gp: Gamepad) {
        power = when {
            gp.a -> -0.5
            gp.b -> 0.5
            else -> 0.0
        }

        lift(power)
    }

    fun lift(power: Double) {
        liftMotor.power = power
    }
}
