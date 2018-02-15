package ro.cnmv.qube.drive

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import ro.cnmv.qube.core.Gamepad
import ro.cnmv.qube.core.RobotOpMode

@TeleOp(name = "Complete Drive", group = "Drive")
class DriveOpMode: RobotOpMode() {

    val timer: ElapsedTime = ElapsedTime()
    var gamepadLastTime: Long = 0
    var gamepadState: Boolean = false
    var jewelLastTime: Long = 0
    var jewelState: Boolean = false

    override fun runOpMode() {
        waitForStart()

        while (opModeIsActive()) {

            if(gamepad2.start && timer.milliseconds() - gamepadLastTime > 1000){
                gamepadState = !gamepadState
                gamepadLastTime = timer.milliseconds().toLong()
            }

            if(gamepad1.x && timer.milliseconds() - jewelLastTime > 1000){
                jewelState = !jewelState
                jewelLastTime = timer.milliseconds().toLong()
            }
            // DRIVE
            robot.driveWithGamepad(gamepad1)

            // GLIDER
            robot.unlockGlider(gamepad1)

            // CUBES INTAKE
            robot.intakeWithGamepad(gamepad2)

            // CUBES LIFT
            robot.liftWithGamepad(gamepad2)

            // CUBES DROP / GLIDER
            when(gamepadState){
                false -> robot.dropWithGamepad(gamepad2)
                else -> robot.gliderWithGamepad(gamepad2)
            }

            // CUBES OPEN
            robot.openWithGamepad(gamepad2)

            // RELIC
            robot.grabRelic(gamepad1)
            robot.liftRelic(gamepad2)

        }
    }
}
