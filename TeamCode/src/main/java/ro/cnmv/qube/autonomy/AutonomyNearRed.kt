package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Near Red", group = "Near Autonomies")
class AutonomyNearRed: AutonomyNearBase() {
    override val directionSign = -1.0

    override val distanceLeft = 50.0
    override val distanceCenter = 31.0
    override val distanceRight = 6.0

}
