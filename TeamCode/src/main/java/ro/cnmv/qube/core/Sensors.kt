package ro.cnmv.qube.core

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.VoltageSensor

interface Sensors {
    val gyro: ModernRoboticsI2cGyro
    val colorSensor: ColorSensor
    val voltageSensor: VoltageSensor

    val heading: Int
        get() = gyro.integratedZValue
}
