package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Near Blue - 55 Ø", group = "Near Autonomies")
class AutonomyNearBlue: AutonomyNearBase() {
    override val directionSign = 1.0

    override val distanceLeft = 1.0
    override val distanceCenter = 17.5
    override val distanceRight = 38.0

}
