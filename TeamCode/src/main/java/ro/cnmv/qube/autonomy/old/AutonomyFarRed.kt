package ro.cnmv.qube.autonomy.old

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "Autonomy Far Red", group = "Old Autonomies")
class AutonomyFarRed: AutonomyFarBase() {
    override val directionSign = -1.0
    override val color = Jewel.Color.RED
    override val distanceLeft = 103.44
    override val distanceCenter = 68.5
    override val distanceRight = 56.0
}
