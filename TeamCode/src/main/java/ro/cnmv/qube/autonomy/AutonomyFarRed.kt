package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Far Red", group = "Autonomies")
class AutonomyFarRed: AutonomyBase() {
    override val directionSign = -1.0
}
