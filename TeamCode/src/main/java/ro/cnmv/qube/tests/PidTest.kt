package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.util.ElapsedTime
import ro.cnmv.qube.core.Gamepad
import ro.cnmv.qube.core.GamepadButton
import ro.cnmv.qube.core.RobotOpMode
import ro.cnmv.qube.pid.RemotePid

@Autonomous(name = "PID Test", group = "Tests")
class PidTest: RobotOpMode() {
    override fun runOpMode() {
        robot.setEncoders(true)

        calibrateGyro()

        val pid = RemotePid()
        pid.beginListening()

        waitForStart()

        val gp = Gamepad(gamepad1)

        val timer1 = ElapsedTime()
        val timer2 = ElapsedTime()
        val timer3 = ElapsedTime()
        val timer4 = ElapsedTime()

        val delay = 250.0;

        var targetAngle = 0.0;

        while (opModeIsActive()) {
            if (gp.checkButtonHold(GamepadButton.A) && timer1.milliseconds() > delay) {
                targetAngle += 5.0;
                timer1.reset()
            } else if (gp.checkButtonHold(GamepadButton.X) && timer2.milliseconds() > delay) {
                targetAngle -= 5.0;
                timer2.reset()
            }

            telemetry.addData("Current heading", robot.heading)
            telemetry.addData("Target heading", targetAngle)
            telemetry.update()

            if (gp.checkButtonHold(GamepadButton.B) && timer3.milliseconds() > delay) {
                robot.rotateTo(targetAngle)
                timer3.reset()
            } else if (gp.checkButtonHold(GamepadButton.Y) && timer4.milliseconds() > delay) {
                val distance = 100.0

                robot.driveDistanceWithPid(distance, pid)

                waitForMs(1000)

                robot.driveDistanceWithPid(-distance, pid)

                timer4.reset()
            }
        }

        pid.shutdown()
    }
}
