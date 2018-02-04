package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.Servo

interface Jewel {
    val jewServo: Servo
    val colorSensor: ColorSensor

    enum class Color {
        RED,
        BLUE,
    }

    /// The color of the jewel.
    val jewelColor: Color
        get() {
            val red = colorSensor.red()
            val blue = colorSensor.blue()

            return if (red > blue) {
                Color.RED
            } else {
                Color.BLUE
            }
        }

    fun openJewelServo(open: Boolean) {
        jewServo.position = if (open) {
            1.0
        } else {
            0.0
        }
    }
}
