package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.NormalizedColorSensor
import com.qualcomm.robotcore.hardware.Servo
import ro.cnmv.qube.core.OpModeAccess

interface Jewel: OpModeAccess {
    val jewServo: Servo
    val jewHitServo: Servo
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
            while (position >= 0.0) {
                position -= 0.1
                jewServo.position = position
                waitMillis(200)
            }
        } else {
            jewServo.position = 1.0
        }
    }

    fun dropJewel(color: Color) {
        val MIDDLE_POSITION = 119.0 / 255.0

        jewHitServo.position = MIDDLE_POSITION
        waitMillis(200)
        openJewelServo(true)

        if(jewelColor == color) jewHitServo.position = 0.0
        else jewHitServo.position = 1.0

        waitMillis(200)

        jewHitServo.position = MIDDLE_POSITION

        openJewelServo(false)
        jewHitServo.position = 0.0
    }
}
