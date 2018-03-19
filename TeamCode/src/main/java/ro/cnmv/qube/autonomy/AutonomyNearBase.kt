package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import ro.cnmv.qube.systems.Jewel

abstract class AutonomyNearBase: AutonomyBase() {
    /// Sign of direction towards crypto box.
    protected abstract val directionSign: Double
    protected abstract val color: Jewel.Color

    override fun postStart() {
        // Read the VuMark now.
        readVuMark()

        robot.dropJewel(color)

        robot.driveDistance(when(directionSign){-1.0 -> 60.0 else -> -50.0},  0.0)
        robot.rotateTo(-85.0)
        robot.rotateTo(-85.0)

        driveToCryptoBox()

        approachCryptoBox()

        dropCube()
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

    private fun approachCryptoBox() {
        setStatus("Approaching crypto box")
        update()

        robot.driveTime(1500, -0.4)

        waitForMs(200)

        robot.driveDistance(15.0, robot.heading.toDouble())
    }
}
