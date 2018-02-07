package ro.cnmv.qube.core

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.util.ElapsedTime

import java.util.Hashtable

class Gamepad(private val gp: Gamepad) {
    private val runtime = ElapsedTime()

    private val lastTime = Hashtable<GamepadButton, Double>()
    private val lastState = Hashtable<GamepadButton, Boolean>()
    private val buttonLock = Hashtable<GamepadButton, Boolean>()

    companion object {
        const val TIME_AFTER_PRESS_BUTTON_TOGGLE = 0.3
    }

    init {
        for (button in GamepadButton.values()) {
            buttonLock[button] = false
            lastState[button] = false
            lastTime[button] = 0.0
        }
    }

    private fun getButtonValue(button: GamepadButton): Boolean =
        when (button) {
            GamepadButton.A -> gp.a
            GamepadButton.B -> gp.b
            GamepadButton.X -> gp.x
            GamepadButton.Y -> gp.y
            GamepadButton.DPAD_DOWN -> gp.dpad_down
            GamepadButton.DPAD_LEFT -> gp.dpad_left
            GamepadButton.DPAD_RIGHT -> gp.dpad_right
            GamepadButton.DPAD_UP -> gp.dpad_up
            GamepadButton.RIGHT_BUMPER -> gp.right_bumper
            GamepadButton.LEFT_BUMPER -> gp.left_bumper
            GamepadButton.START -> gp.start
        }

    fun checkButtonHold(button: GamepadButton): Boolean = getButtonValue(button)

    fun checkButtonToggle(button: GamepadButton): Boolean {
        val buttonValue = getButtonValue(button)
        val lastValue = lastState[button] ?: false

        // If false, no state change is requested.
        if (!buttonValue) {
            return lastValue
        } else {
            // Record new state.

            // Unlock if enough time passed.
            val lastButtonTime = lastTime[button] ?: 0.0
            if (runtime.time() - lastButtonTime > TIME_AFTER_PRESS_BUTTON_TOGGLE) {
                lastTime[button] = runtime.time()
                buttonLock[button] = false
            }

            // If its currently locked.
            return if (buttonLock[button] == true) {
                lastValue
            } else {
                // Toggle state.
                lastState[button] = !lastValue

                // Lock state.
                buttonLock[button] = true

                // Update time.
                lastTime[button] = runtime.time()

                !lastValue
            }
        }
    }
}
