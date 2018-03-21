package ro.cnmv.qube.autonomy.old

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import ro.cnmv.qube.systems.Jewel

@Disabled
@Autonomous(name = "Autonomy Far Blue", group = "Old Autonomies")
class AutonomyFarBlue: AutonomyFarBase() {
    override val directionSign = 1.0
    override val color = Jewel.Color.BLUE
    override val distanceLeft = 38.0
    override val distanceCenter = 67.0
    override val distanceRight = 86.44
}
