package ro.cnmv.qube.core

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro
import com.qualcomm.robotcore.hardware.ColorSensor

interface Sensors {
    val gyro: ModernRoboticsI2cGyro
    val colorSensor: ColorSensor

    val heading: Int
        get() = gyro.integratedZValue
}
