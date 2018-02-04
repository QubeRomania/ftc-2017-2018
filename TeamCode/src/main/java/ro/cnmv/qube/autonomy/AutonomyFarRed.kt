package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous

@Autonomous(name = "Autonomy Far Blue", group = "Autonomies")
class AutonomyFarRed: AutonomyBase() {
    override val directionSign = -1.0
}
