package ro.cnmv.qube.systems

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

interface CryptoAlign {
    val backRangeSensor: ModernRoboticsI2cRangeSensor

    val backDistance
        get() = backRangeSensor.getDistance(DistanceUnit.CM)
}
