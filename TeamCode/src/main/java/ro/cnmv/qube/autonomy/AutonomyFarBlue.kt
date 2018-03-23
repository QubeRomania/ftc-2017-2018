package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "Autonomy Far Blue", group = "NearAutonomies")
class AutonomyFarBlue: AutonomyBase() {
    override val ourColor = Jewel.Color.BLUE
    override val vuforiaTimeout = 1000L
    override fun postStart() {
        robot.driveDistance(-35.0, 0.0)
        robot.rotateTo(90.0)
        robot.driveDistance(-20.0, 90.0)
        robot.runToColumn(4 - vuMark.column(), 1.0, 90.0)
        dropCube()
    }
}
