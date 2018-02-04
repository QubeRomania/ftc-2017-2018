package ro.cnmv.qube.core

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro

interface Gyro {
    val gyro: ModernRoboticsI2cGyro

    val heading: Int
        get() = gyro.integratedZValue
}
