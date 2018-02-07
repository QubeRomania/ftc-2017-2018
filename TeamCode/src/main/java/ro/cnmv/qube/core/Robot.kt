package ro.cnmv.qube.core

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro
import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import ro.cnmv.qube.systems.*

class Robot(private val opMode: RobotOpMode):
        DriveMotors, Gyro, Drive, CubesIntake, CubesLift, CubesDrop, Jewel, OpModeAccess by opMode {
    private val hwMap = opMode.hardwareMap

    // VUFORIA
    val vuforia = VuforiaImpl(hwMap.appContext)

    // MOTORS
    override val frontLeft: DcMotor = initMotor("frontLeftMotor", Direction.REVERSE)
    override val frontRight: DcMotor = initMotor("frontRightMotor", Direction.FORWARD)

    override val backLeft: DcMotor = initMotor("backLeftMotor", Direction.REVERSE)
    override val backRight: DcMotor = initMotor("backRightMotor", Direction.FORWARD)

    override val intakeLeft: DcMotor = initMotor("leftIntakeMotor", Direction.FORWARD)
    override val intakeRight: DcMotor = initMotor("rightIntakeMotor", Direction.REVERSE)

    override val intakeOpen: DcMotor = initMotor("intakeOpenMotor", Direction.FORWARD)

    /// Robot's battery voltage in Volts.
    val voltage by lazy {
        val voltageSensor = hwMap.voltageSensor.first()

        // Read voltage only once, when starting OpMode.
        voltageSensor.voltage
    }

    val volagePowerConstant
        get() = 12.0 / voltage

    // SENSORS
    override val gyro = initGyro()
    override val colorSensor = initColorSensor()

    // SERVOS
    override val leftLiftServo = initCRServo("leftLiftServo", Direction.FORWARD)
    override val rightLiftServo = initCRServo("rightLiftServo", Direction.REVERSE)
    override val leftDropServo = initCRServo("leftDropServo", Direction.FORWARD)
    override val rightDropServo = initCRServo("rightDropServo", Direction.REVERSE)
    override val jewServo = initServo("jewServo")

    /// Initializes a DC motor.
    private fun initMotor(name: String, direction: Direction): DcMotor {
        val motor = hwMap.dcMotor[name]

        motor.power = 0.0
        motor.direction = direction
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        return motor
    }

    /// Initializes the robot's central gyroscope.
    private fun initGyro(): ModernRoboticsI2cGyro {
        val gyro = hwMap.gyroSensor["gyro"] as ModernRoboticsI2cGyro

        gyro.headingMode = ModernRoboticsI2cGyro.HeadingMode.HEADING_CARTESIAN

        return gyro
    }

    private fun initColorSensor(): ColorSensor {
        val colorSensor = hwMap.colorSensor["jewColor"]

        colorSensor.enableLed(true)

        return colorSensor
    }

    /// Initializes a continuously rotating servo.
    private fun initCRServo(name: String, direction: Direction): CRServo {
        val servo = hwMap.crservo[name]

        servo.direction = direction
        servo.power = 0.0

        return servo
    }

    // Initializes a regular servo.
    private fun initServo(name: String): Servo {
        val servo = hwMap.servo[name]

        servo.direction = Servo.Direction.FORWARD
        servo.position = 0.0

        return servo
    }
}
