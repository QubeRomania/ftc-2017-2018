package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.NormalizedColorSensor
import com.qualcomm.robotcore.hardware.Servo
import ro.cnmv.qube.core.OpModeAccess

interface Jewel: OpModeAccess {
    val jewServo: Servo
    val jewHitServo: Servo
    val colorSensor: NormalizedColorSensor

    companion object {
        const val JEWEL_ARM_TOP_POSITION = 200.0 / 255.0
        const val JEWEL_ARM_BOTTOM_POSITION = 0.0 / 255.0

        const val JEWEL_HIT_MIDDLE_POSITION = 119.0 / 255.0
    }

    enum class Color {
        RED,
        BLUE,
    }

    /// The color of the jewel.
    val jewelColor: Color
        get() {
            val color = colorSensor.normalizedColors

            tele.addData("Red", color.red)
            tele.addData("Blue", color.blue)
            tele.update()

            return if (color.blue > color.red) {
                Color.BLUE
            } else {
                Color.RED
            }
        }

    fun openJewelServo(open: Boolean) {
        if (open) {
            var position = jewServo.position
            while (position >= JEWEL_ARM_BOTTOM_POSITION) {
                position -= 0.1
                jewServo.position = position
                waitMillis(200)
            }
        } else {
            jewServo.position = JEWEL_ARM_TOP_POSITION
        }
    }

    fun dropJewel(ourColor: Color) {
        jewHitServo.position = JEWEL_HIT_MIDDLE_POSITION
        waitMillis(200)
        openJewelServo(true)

        waitMillis(800)

        if(jewelColor == ourColor) jewHitServo.position = 0.0
        else jewHitServo.position = 1.0

        waitMillis(400)

        jewHitServo.position = JEWEL_HIT_MIDDLE_POSITION

        openJewelServo(false)

        jewHitServo.position = 1.0

        waitMillis(200)
    }
}
