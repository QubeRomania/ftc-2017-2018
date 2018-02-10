package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Far Blue", group = "Far Autonomies")
class AutonomyFarBlue: AutonomyFarBase() {
    override val directionSign = 1.0

    override val distanceLeft = 50.0
    override val distanceCenter = 72.0
    override val distanceRight = 91.44
}
