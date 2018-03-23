package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "Autonomy Near Blue", group = "Near Autonomies")
class AutonomyNearBlue: AutonomyBase() {
    override val ourColor = Jewel.Color.BLUE
    override val vuforiaTimeout = 2000L
    override fun postStart() {
        robot.driveDistance(-52.0, 0.0)
        robot.driveDistance(-4.0, 0.0)
        robot.rotateTo(0.0)
        robot.runToColumn(4 - vuMark.column(), 1.0, 0.0)
        dropCube()
    }
}
