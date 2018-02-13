package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Far Red", group = "Far Autonomies")
class AutonomyFarRed: AutonomyFarBase() {
    override val directionSign = -1.0

    override val distanceLeft = 103.44
    override val distanceCenter = 80.0
    override val distanceRight = 61.0
}
