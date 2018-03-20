package ro.cnmv.qube.core

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor
import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import ro.cnmv.qube.systems.*
import ro.cnmv.qube.systems.VuforiaImpl

class Robot(private val opMode: RobotOpMode):
        DriveMotors, Gyro, Drive, CryptoAlign, CubesIntake, CubesLift, CubesDrop, Jewel, OpModeAccess by opMode {

    private val hwMap = opMode.hardwareMap

    // VUFORIA
    val vuforia: Vuforia by lazy {
        VuforiaImpl(hwMap.appContext)
    }

    // MOTORS
    override val frontLeft: DcMotor = initMotor("frontLeftMotor", Direction.REVERSE)
    override val frontRight: DcMotor = initMotor("frontRightMotor", Direction.FORWARD)

    override val backLeft: DcMotor = initMotor("backLeftMotor", Direction.REVERSE)
    override val backRight: DcMotor = initMotor("backRightMotor", Direction.FORWARD)

    override val intakeLeft: DcMotor = initMotor("leftIntakeMotor", Direction.FORWARD)
    override val intakeRight: DcMotor = initMotor("rightIntakeMotor", Direction.REVERSE)

    override val liftMotor: DcMotor = initMotor("liftMotor", Direction.REVERSE)

    override var power: Double = 0.0

    // SENSORS
    override val gyro = initGyro()
    override val colorSensor = initColorSensor()
    override val backRangeSensor = hwMap.get(ModernRoboticsI2cRangeSensor::class.java, "backRangeSensor")!!

    // SERVOS
    override val leftDropServo = initServo("leftDropServo")
    override val rightDropServo = initServo("rightDropServo")
    override val jewServo = initServo("jewServo")
    override val jewHitServo = initServo("jewHitServo")


    init {
        jewServo.position = Jewel.JEWEL_ARM_TOP_POSITION

        val LEFT_DOWN_POSITION = 0.0
        val LEFT_UP_POSITION = 1.0
        val RIGHT_DOWN_POSITION = 133.0 / 255.0
        val RIGHT_UP_POSITION = 240.0 / 255.0

        leftDropServo.scaleRange(LEFT_DOWN_POSITION, LEFT_UP_POSITION)
        leftDropServo.direction = Servo.Direction.REVERSE
        leftDropServo.position = 0.0

        rightDropServo.scaleRange(RIGHT_DOWN_POSITION, RIGHT_UP_POSITION)
        rightDropServo.direction = Servo.Direction.FORWARD
        rightDropServo.position = 0.0

        liftMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

    // Initializes Vuforia in a new thread to speed up robot start up.
    fun initVuforia() {
        // Force the lazy initializer to run in a separate thread.
        object: Thread() {
            override fun run() {
                vuforia.deactivate()
            }
        }.start()
    }

    // Stops the robot by shutting down all the hardware.
    fun stop() {
        stopMotors()

        intakeLeft.power = 0.0
        intakeRight.power = 0.0

        lift(0.0)
        dropCubesAuto(false)
    }

    // Initializes a DC motor.
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

    private fun initColorSensor(): NormalizedColorSensor {
        val colorSensor = hwMap.colorSensor["jewColor"]

        colorSensor.enableLed(true)

        return colorSensor as NormalizedColorSensor
    }

    // Initializes a regular servo.
    private fun initServo(name: String): Servo {
        val servo = hwMap.servo[name]

        servo.direction = Servo.Direction.FORWARD

        return servo
    }
}
