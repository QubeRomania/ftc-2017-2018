package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "Autonomy Near Red", group = "Near Autonomies")
class AutonomyNearRed: AutonomyBase(){
    override val ourColor = Jewel.Color.RED
    override val vuforiaTimeout = 1000L
    override fun postStart(){
        robot.driveDistance(65.0, 0.0)
        robot.rotateTo(180.0)
        robot.driveDistance(-12.0, 180.0)
        robot.runToColumn(vuMark.column(), -1.0, 180.0)
        dropCube()
    }
}
