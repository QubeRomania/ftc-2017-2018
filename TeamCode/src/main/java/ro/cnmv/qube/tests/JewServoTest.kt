package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode
import ro.cnmv.qube.systems.Jewel

@Autonomous(name = "JewServoTest", group = "Tests")
class JewServoTest: RobotOpMode() {
    override fun runOpMode(){
        waitForStart()
        robot.dropJewel(Jewel.Color.RED)
    }
}
