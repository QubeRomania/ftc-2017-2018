package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import ro.cnmv.qube.core.RobotOpMode
import ro.cnmv.qube.systems.Jewel

abstract class AutonomyBase: RobotOpMode() {
    companion object {
        const val DISTANCE_LEFT = 72.05;
        const val DISTANCE_CENTER = 91.44;
        const val DISTANCE_RIGHT = 110.82;
    }

    /// Sign of direction towards crypto box.
    protected abstract val directionSign: Double

    /// Detected VuMark.
    private var vuMark = RelicRecoveryVuMark.UNKNOWN

    override fun runOpMode() {
        robot.setEncoders(true)

        calibrateGyro()

        waitForStart()

        detectJewel()

        robot.rotateTo(90.0)

        readVuMark()

        driveToCryptoBox()
    }

    private fun detectJewel() {
        robot.openJewelServo(true)

        // Determine angle of rotation.
        val angle = when (robot.jewelColor) {
            Jewel.Color.RED -> 10.0
            else -> -10.0
        }

        robot.rotateTo(angle)

        robot.openJewelServo(false)
    }

    private fun readVuMark() {
        val vuforia = robot.vuforia

        vuforia.activate()

        val timer = ElapsedTime()
        while (opModeIsActive() && timer.milliseconds() < 2000) {
            vuMark = robot.vuforia.vuMark
        }

        vuforia.deactivate()
    }

    private fun driveToCryptoBox() {
        robot.rotateTo(0.0)
        waitForMs(200)

        val distance = vuMark.distance
        robot.driveDistance(directionSign * distance)

        robot.rotateTo(90.0)
        waitForMs(200)

        dropCube()
    }

    /// Converts a detected VuMark to a distance we must travel.
    private val RelicRecoveryVuMark.distance
        get() = when (this) {
            RelicRecoveryVuMark.LEFT -> DISTANCE_LEFT
            RelicRecoveryVuMark.RIGHT -> DISTANCE_RIGHT
            else -> DISTANCE_CENTER
        }


    private fun dropCube() {
        robot.drop(1.0)
        waitForMs(200)
        robot.drop(0.0)
    }
}
