package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.LED

interface Leds {
    val leftLed: LED
    val rightLed: LED
}
