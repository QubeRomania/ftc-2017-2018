package ro.cnmv.qube.autonomy.old

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import ro.cnmv.qube.systems.Jewel

@Disabled
@Autonomous(name = "Autonomy Near Red", group = "Old Autonomies")
class AutonomyNearRed: AutonomyNearBase() {
    override val directionSign = -1.0
    override val color = Jewel.Color.RED
    override val distanceLeft = 47.0
    override val distanceCenter = 32.0
    override val distanceRight = 11.0

}
