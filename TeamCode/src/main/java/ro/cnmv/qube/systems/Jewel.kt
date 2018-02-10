package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.NormalizedColorSensor
import com.qualcomm.robotcore.hardware.Servo
import ro.cnmv.qube.core.OpModeAccess

interface Jewel: OpModeAccess {
    val jewServo: Servo
    val colorSensor: NormalizedColorSensor

    enum class Color {
        RED,
        BLUE,
    }

    /// The color of the jewel.
    val jewelColor: Color
        get() {
            val color = colorSensor.normalizedColors

            return if (color.blue > color.red) {
                Color.BLUE
            } else {
                Color.RED
            }
        }

    fun openJewelServo(open: Boolean) {
        if (open) {
            var position = 0.0
            while (position <= LOWER_POSITION) {
                position += 0.1
                jewServo.position = position
                waitMillis(200)
            }
        } else {
            jewServo.position = 0.0
        }
    }

    companion object {
        const val LOWER_POSITION = 0.56
    }
}
