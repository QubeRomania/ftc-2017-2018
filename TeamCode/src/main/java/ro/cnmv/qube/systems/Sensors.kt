package ro.cnmv.qube.systems

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.DigitalChannel

interface Sensors {
    val leftAnalogSensor: AnalogInput
    val rightAnalogSensor: AnalogInput

    val leftDigitalSensor: DigitalChannel
    val rightDigitalSensor: DigitalChannel
}
