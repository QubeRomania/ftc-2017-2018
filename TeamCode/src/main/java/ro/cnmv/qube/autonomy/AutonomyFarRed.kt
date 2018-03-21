package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "Autonomy Far Red", group = "Far Autonomies")
class AutonomyFarRed: AutonomyBase() {
    override val ourColor = Jewel.Color.RED
    override fun postStart() {
        robot.driveDistance(55.0, 0.0)
        robot.rotateTo(90.0)
        robot.driveDistance(-15.0, 90.0)
        robot.runToColumn(vuMark.column(), -1.0, 90.0)
        dropCube()
    }
}
