package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "Autonomy Far Blue", group = "Far Autonomies")
class AutonomyFarBlue: AutonomyFarBase() {
    override val directionSign = 1.0
    override val color = Jewel.Color.RED
    override val distanceLeft = 38.0
    override val distanceCenter = 67.0
    override val distanceRight = 86.44
}
