package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import ro.cnmv.qube.core.RobotOpMode
import ro.cnmv.qube.systems.Jewel

abstract class AutonomyFarBase : RobotOpMode() {
    companion object {
        const val JEWEL_HIT_ROTATION = 7.0
    }

    // VUFORIA
    val vuforia = robot.vuforia


    /// Sign of direction towards crypto box.
    protected abstract val directionSign: Double

    protected abstract val distanceLeft: Double
    protected abstract val distanceCenter: Double
    protected abstract val distanceRight: Double

    /// Detected VuMark.
    private var vuMark = RelicRecoveryVuMark.UNKNOWN

    private var cryptoBoxDirection = 90.0

    override fun runOpMode() {
        robot.resetEncoders()

        // Adjust the crypto box's direction for an error.
        cryptoBoxDirection = 85.0

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
        robot.rotateToPower(rotation, 0.4)

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

        vuforia.activate()
        waitForMs(200)

        val timer = ElapsedTime()
        while (opModeIsActive() && vuMark == RelicRecoveryVuMark.UNKNOWN && timer.milliseconds() < 1000) {
            vuMark = vuforia.vuMark

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

        val distance = vuMark.distance + if (directionSign == -1.0) { 10.0 } else { 0.0 }
        robot.driveDistance(directionSign * -distance, 0.0)

        robot.rotateTo(cryptoBoxDirection)
        robot.rotateTo(cryptoBoxDirection)

        waitForMs(200)
    }

    /// Converts a detected VuMark to a distance we must travel.
    private val RelicRecoveryVuMark.distance
        get() = when (this) {
            RelicRecoveryVuMark.LEFT -> distanceLeft
            RelicRecoveryVuMark.RIGHT -> distanceRight
            else -> distanceCenter
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

        robot.drop(-0.6)
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
