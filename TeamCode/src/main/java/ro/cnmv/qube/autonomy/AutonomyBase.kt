package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import ro.cnmv.qube.core.RobotOpMode
import ro.cnmv.qube.systems.Jewel

abstract class AutonomyBase: RobotOpMode() {
    companion object {
        const val JEWEL_HIT_ROTATION = 7.0

        const val DISTANCE_LEFT = 50.0
        const val DISTANCE_CENTER = 72.0
        const val DISTANCE_RIGHT = 91.44
    }

    /// Sign of direction towards crypto box.
    protected abstract val directionSign: Double

    /// Detected VuMark.
    private var vuMark = RelicRecoveryVuMark.UNKNOWN

    private var cryptoBoxDirection = 90.0

    override fun runOpMode() {
        robot.resetEncoders()

        // Adjust the crypto box's direction for an error.
        cryptoBoxDirection = 90.0 - 3 * directionSign

        calibrateGyro()

        waitForStart()

        detectJewel()

        driveToCryptoBox()

        approachCryptoBox()

        dropCube()
    }

    private fun detectJewel() {
        setStatus("Lowering jewel servo")
        update()

        // Lower jewel arm.
        robot.openJewelServo(true)

        // Wait for arm to lower.
        waitForMs(500)

        // Read the VuMark now.
        readVuMark()

        setStatus("Detecting jewel color")
        update()

        // Determine angle of rotation.
        val angle = when (robot.jewelColor) {
            Jewel.Color.RED -> JEWEL_HIT_ROTATION
            else -> -JEWEL_HIT_ROTATION
        }

        // Rotate in order to hit the jewel.
        val rotation = angle * directionSign
        robot.rotateTo(rotation)

        setStatus("Lifting jewel servo arm")
        update()

        // Lift the arm.
        robot.openJewelServo(false)

        setStatus("Rotating to straight direction")
        update()

        // Rotate back to straight direction.
        robot.rotateTo(0.0)
        waitForMs(200)
    }

    private fun readVuMark() {
        setStatus("Reading VuMark")
        update()

        val vuforia = robot.vuforia

        vuforia.activate()
        waitForMs(200)

        val timer = ElapsedTime()
        while (opModeIsActive() && vuMark == RelicRecoveryVuMark.UNKNOWN && timer.milliseconds() < 1000) {
            vuMark = robot.vuforia.vuMark

            waitForMs(50)
        }

        setStatus("VuMark is $vuMark")
        update()

        waitForMs(500)

        vuforia.deactivate()
    }

    private fun driveToCryptoBox() {
        setStatus("Driving to crypto box.")
        update()

        val distance = vuMark.distance
        robot.driveDistance(directionSign * -distance, 0.0)

        robot.rotateTo(cryptoBoxDirection * directionSign)

        waitForMs(200)
    }

    /// Converts a detected VuMark to a distance we must travel.
    private val RelicRecoveryVuMark.distance
        get() = when (this) {
            RelicRecoveryVuMark.LEFT -> DISTANCE_LEFT
            RelicRecoveryVuMark.RIGHT -> DISTANCE_RIGHT
            else -> DISTANCE_CENTER
        }

    private fun approachCryptoBox() {
        setStatus("Approaching crypto box")
        update()

        robot.driveTime(1500, -0.4)

        waitForMs(200)

        robot.driveDistance(15.0, robot.heading.toDouble())
    }

    private fun dropCube() {
        setStatus("Dropping cube")
        update()

        robot.drop(-0.5)
        waitForMs(900)
        robot.drop(0.0)

        waitForMs(1000)

        setStatus("Pushing cube in crypto box")
        update()

        robot.driveDistance(5.0, robot.heading.toDouble())

        robot.driveDistance(-17.0, robot.heading.toDouble())

        robot.driveDistance(3.0, robot.heading.toDouble())
    }
}
