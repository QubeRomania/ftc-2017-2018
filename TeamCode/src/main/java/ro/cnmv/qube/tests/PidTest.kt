package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.hardware.PIDCoefficients
import com.qualcomm.robotcore.util.ElapsedTime
import ro.cnmv.qube.core.Gamepad
import ro.cnmv.qube.core.GamepadButton
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "PID Test", group = "Tests")
class PidTest: RobotOpMode() {
    override fun runOpMode() {
        calibrateGyro()

        //val pid = RemotePid()
        //pid.beginListening()

        val pid = PIDCoefficients(0.1, 0.02, 0.1)

        waitForStart()

        val gp = Gamepad(gamepad1)

        var targetAngle = 0.0
        var lastStates = arrayOf(false, false, false, false)

        while (opModeIsActive()) {
            if (lastStates[0] != gp.checkButtonToggle(GamepadButton.A)) {
                targetAngle += 5.0
                lastStates[0] = !lastStates[0]
            }

            if (lastStates[1] != gp.checkButtonToggle(GamepadButton.X)) {
                targetAngle -= 5.0
                lastStates[1] = !lastStates[1]
            }

            if (lastStates[2] != gp.checkButtonToggle(GamepadButton.B)) {
                robot.rotateTo(targetAngle)
                lastStates[2] = !lastStates[2]
            }

            if (gp.checkButtonToggle(GamepadButton.Y)) {
                val distance = 100.0

                robot.driveDistanceWithPid(distance, targetAngle, pid)

                waitForMs(1000)

                robot.driveDistanceWithPid(-distance, targetAngle, pid)
                lastStates[3] = !lastStates[3]
            }


            tele.addData("Current heading", robot.heading)
            tele.addData("Target heading", targetAngle)
            telemetry.update()
        }

        //pid.shutdown()
        robot.stop()
    }
}
