package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Far Blue", group = "Far Autonomies")
class AutonomyFarBlue: AutonomyFarBase() {
    override val directionSign = 1.0

    override val distanceLeft = 38.0
    override val distanceCenter = 67.0
    override val distanceRight = 86.44
}
