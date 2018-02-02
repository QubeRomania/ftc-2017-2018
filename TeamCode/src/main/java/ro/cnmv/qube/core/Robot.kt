package ro.cnmv.qube.core

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro
import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction

class Robot(private val hwMap: HardwareMap): Sensors, Servos {
    override val voltageSensor: VoltageSensor = hwMap.voltageSensor.first()

    override val gyro = hwMap.gyroSensor.get("gyro") as ModernRoboticsI2cGyro

    override val colorSensor: ColorSensor = hwMap.colorSensor.get("jewColor")

    override val leftLiftServo = initCRServo("leftLiftServo", Direction.FORWARD)
    override val rightLiftServo = initCRServo("rightLiftServo", Direction.REVERSE)
    override val leftDropServo = initCRServo("leftDropServo", Direction.FORWARD)
    override val rightDropServo = initCRServo("rightDropServo", Direction.REVERSE)
    override val jewServo = initServo("jewServo")

    init {
        initGyro()

        leftDropServo.direction = Direction.FORWARD
    }

    private fun initGyro() {
        gyro.headingMode = ModernRoboticsI2cGyro.HeadingMode.HEADING_CARTESIAN
        colorSensor.enableLed(true)
    }

    private fun initCRServo(name: String, direction: Direction): CRServo {
        val servo = hwMap.crservo[name]

        servo.direction = direction
        servo.power = 0.0

        return servo
    }

    private fun initServo(name: String): Servo {
        val servo = hwMap.servo[name]

        servo.direction = Servo.Direction.FORWARD
        servo.position = 0.0

        return servo
    }
}
