package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import ro.cnmv.qube.systems.Jewel

abstract class AutonomyNearBase: AutonomyBase() {

    companion object {
        const val JEWEL_HIT_ROTATION = 7.0
    }

    /// Sign of direction towards crypto box.
    protected abstract val directionSign: Double

    protected abstract val distanceLeft: Double
    protected abstract val distanceCenter: Double
    protected abstract val distanceRight: Double

    /// Detected VuMark.
    private var vuMark = RelicRecoveryVuMark.UNKNOWN

    private var correctionHeading = 0

    override fun postStart() {
        // Read the VuMark now.
        readVuMark()

        detectJewel()

        robot.driveDistance(when(directionSign){-1.0 -> 60.0 else -> -50.0},  0.0)
        robot.rotateTo(-85.0)
        robot.rotateTo(-85.0)
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

        setStatus("Detecting jewel color")
        update()

        // Determine angle of rotation.
        val angle = when (robot.jewelColor) {
            Jewel.Color.RED -> AutonomyNearBase.JEWEL_HIT_ROTATION
            else -> -AutonomyNearBase.JEWEL_HIT_ROTATION
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

        val vuforia = robot.vuforia

        vuforia.activate()
        waitForMs(200)

        val timer = ElapsedTime()
        while (opModeIsActive() && vuMark == RelicRecoveryVuMark.UNKNOWN && timer.milliseconds() < 1500) {
            vuMark = vuforia.vuMark
        }

        setStatus("VuMark is $vuMark")
        update()

        waitForMs(500)

        vuforia.deactivate()
    }

    private fun driveToWall(){
        robot.driveDistance(-60.0 * directionSign, 0.0)

        waitForMs(200)

        robot.rotateTo(-90.0 * directionSign)

        robot.driveTime(2000, -0.5)
        correctionHeading = robot.heading
        waitForMs(200)
    }

    private fun driveToCryptoBox() {
        setStatus("Driving to crypto box.")
        update()

        val distance = vuMark.distance
        robot.driveDistance(-distance, robot.heading.toDouble())

        robot.rotateTo(when(directionSign){ 1.0 -> -0.0 else -> -172.0})
        robot.rotateTo(when(directionSign){ 1.0 -> -0.0 else -> -172.0})

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

        robot.drop(-0.5)
        waitForMs(900)
        robot.drop(0.0)

        waitForMs(1000)

        setStatus("Pushing cube in crypto box")
        update()

        robot.driveDistance(5.0, robot.heading.toDouble())

        robot.driveTime(2000, -0.4)

        robot.driveDistance(3.0, robot.heading.toDouble())
    }
}
