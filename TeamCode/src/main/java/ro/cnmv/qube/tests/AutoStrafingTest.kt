package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Auto Strafing Test", group = "Tests")
class AutoStrafingTest: RobotOpMode() {
    override fun runOpMode() {
        calibrateGyro()

        waitForStart()

        /*
        TODO: back distance correction.
        val correction = {
            val currentDistance = robot.backDistance
            val error = targetDistance - currentDistance

            val P = 0.025
            val I = 0.005
            val D = 0.02

            val pid = P * error + I * (error + lastDistanceError) + D * (error - lastDistanceError)

            lastDistanceError = error

            pid
        }()
        */

        robot.runToColumn(3, 1.0)

        robot.stop()
    }
}
