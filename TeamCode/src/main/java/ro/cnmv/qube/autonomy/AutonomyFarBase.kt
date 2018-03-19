package ro.cnmv.qube.autonomy

import android.graphics.Color
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import ro.cnmv.qube.systems.Jewel

abstract class AutonomyFarBase: AutonomyBase() {
    /// Sign of direction towards crypto box.
    protected abstract val directionSign: Double
    protected abstract val color: Jewel.Color

    // Adjust the crypto box's direction for an error.
    private var cryptoBoxDirection = 85.0

    override fun postStart() {
        readVuMark()

        robot.dropJewel(color)

        driveToCryptoBox()

        approachCryptoBox()

        dropCube()
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

    private fun approachCryptoBox() {
        setStatus("Approaching crypto box")
        update()

        robot.driveTime(1500, -0.4)

        waitForMs(200)

        robot.driveDistance(15.0, robot.heading.toDouble())
    }
}
