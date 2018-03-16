package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import ro.cnmv.qube.core.DriveMotors
import ro.cnmv.qube.core.RobotOpMode

/** Doesn't work w */

@Autonomous(name = "Drive Test", group = "Test")
class DriveTest(private val OpMode: RobotOpMode): RobotOpMode(), DriveMotors{

    private val hwMap = OpMode.hardwareMap

    override val frontLeft: DcMotor = initMotor("frontLeftMotor", DcMotorSimple.Direction.REVERSE)
    override val frontRight: DcMotor = initMotor("frontRightMotor", DcMotorSimple.Direction.FORWARD)

    override val backLeft: DcMotor = initMotor("backLeftMotor", DcMotorSimple.Direction.REVERSE)
    override val backRight: DcMotor = initMotor("backRightMotor", DcMotorSimple.Direction.FORWARD)

    override fun runOpMode() {
        waitForStart()
        while (opModeIsActive()) {
            driveWithGamepad(gamepad1)
        }
    }

    private fun driveWithGamepad(gp: Gamepad) {
        val x = -gp.left_stick_x.toDouble()
        val y = -gp.left_stick_y.toDouble()
        val z = -gp.right_stick_x.toDouble()

        setPower(
                y - x - z,
                y + x + z,
                y + x - z,
                y - x + z
        )
    }

    private fun initMotor(name: String, direction: DcMotorSimple.Direction): DcMotor {
        val motor = hwMap.dcMotor[name]

        motor.power = 0.0
        motor.direction = direction
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        return motor
    }
}
