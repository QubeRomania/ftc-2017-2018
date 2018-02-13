package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Near Red - 65 Ø", group = "Near Autonomies")
class AutonomyNearRed: AutonomyNearBase() {
    override val directionSign = -1.0

    override val distanceLeft = 56.0
    override val distanceCenter = 36.0
    override val distanceRight = 17.0

}
