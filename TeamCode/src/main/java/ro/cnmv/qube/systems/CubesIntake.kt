package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad

interface CubesIntake {
    val intakeLeft: DcMotor
    val intakeRight: DcMotor

    /// Controls cube intake.
    fun intakeWithGamepad(gp: Gamepad) {
        intakeLeft.power = roundPower(gp.left_stick_y)
        intakeRight.power = roundPower(gp.right_stick_y)
    }

    companion object {
        /// Rounds a number to nearest multiple of 0.5
        fun roundPower(power: Float): Double = Math.round(power * 2) / 2.0
    }
}
