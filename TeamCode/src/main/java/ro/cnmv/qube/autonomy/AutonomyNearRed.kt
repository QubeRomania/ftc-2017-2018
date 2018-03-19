package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "Autonomy Near Red", group = "Near Autonomies")
class AutonomyNearRed: AutonomyNearBase() {
    override val directionSign = -1.0
    override val color = Jewel.Color.BLUE
    override val distanceLeft = 56.0
    override val distanceCenter = 36.0
    override val distanceRight = 17.0

}
