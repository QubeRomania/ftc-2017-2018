package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Cubes Drop Test", group = "Tests")
class CubeDropTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()

        robot.dropCubesAuto(true)

        robot.waitMillis(3000)

        robot.dropCubesAuto(false)
    }
}
