package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad

interface CubesIntake {
    val intakeLeft: DcMotor
    val intakeRight: DcMotor
    val intakeOpen: DcMotor

    /// Opens and closes the intake funnel.
    fun openWithGamepad(gp: Gamepad) {
        intakeOpen.power = when {
            gp.x -> 1.0
            gp.y -> -1.0
            else -> 0.0
        }
    }

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
