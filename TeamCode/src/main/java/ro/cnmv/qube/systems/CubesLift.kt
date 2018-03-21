package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.DcMotor
import ro.cnmv.qube.core.Gamepad
import ro.cnmv.qube.core.GamepadButton
import ro.cnmv.qube.core.OpModeAccess

interface CubesLift: OpModeAccess {
    val liftMotor: DcMotor

    companion object {
        const val LIFT_SPEED = 1

        const val LIFT_BOTTOM = 0
        // TODO: MIDDLE
        const val LIFT_TOP = 1500
    }

    fun liftWithGamepad(gp: Gamepad) {
        if (gp.checkButtonToggle(GamepadButton.LEFT_BUMPER)) {
            tele.addData("Mode", "manual")
            // Manual mode
            var newPosition = liftMotor.currentPosition + when {
                gp.checkButtonHold(GamepadButton.A) -> +LIFT_SPEED
                gp.checkButtonHold(GamepadButton.B) -> -LIFT_SPEED
                else -> 0
            }

            if (newPosition < LIFT_BOTTOM) {
                newPosition = LIFT_BOTTOM
            } else if (newPosition > LIFT_TOP) {
                newPosition = LIFT_TOP
            }

            liftPosition(newPosition)
        } else {
            tele.addData("Mode", "auto")
            // Auto mode
            when {
                gp.checkButtonHold(GamepadButton.A) -> liftPosition(LIFT_TOP)
                gp.checkButtonHold(GamepadButton.B) -> liftPosition(LIFT_BOTTOM)
            }
        }
    }

    fun liftPosition(position: Int) {
        liftMotor.targetPosition = position
        liftMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
    }
}
