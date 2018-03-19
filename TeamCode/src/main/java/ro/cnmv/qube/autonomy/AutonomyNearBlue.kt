package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "Autonomy Near Blue", group = "Near Autonomies")
class AutonomyNearBlue: AutonomyNearBase() {
    override val directionSign = 1.0
    override val color = Jewel.Color.RED
    override val distanceLeft = 1.0
    override val distanceCenter = 17.5
    override val distanceRight = 38.0

}
