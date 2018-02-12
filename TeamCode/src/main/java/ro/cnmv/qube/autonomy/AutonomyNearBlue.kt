package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Near Blue", group = "Near Autonomies")
class AutonomyNearBlue: AutonomyNearBase() {
    override val directionSign = 1.0

    override val distanceLeft = 10.0
    override val distanceCenter = 22.5
    override val distanceRight = 55.0

}
