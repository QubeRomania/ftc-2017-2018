package ro.cnmv.qube.drive

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import ro.cnmv.qube.core.Gamepad
import ro.cnmv.qube.core.RobotOpMode

@TeleOp(name = "Complete Drive", group = "Drive")
class DriveOpMode: RobotOpMode() {
    private val timer: ElapsedTime = ElapsedTime()
    private var jewelLastTime: Long = 0
    private var jewelState: Boolean = false

    override fun runOpMode() {
        waitForStart()

        while (opModeIsActive()) {
            if(gamepad1.x && timer.milliseconds() - jewelLastTime > 1000){
                jewelState = !jewelState
                jewelLastTime = timer.milliseconds().toLong()
                robot.jewServo.position = when(jewelState){true -> 1.0 else -> 0.0}
            }
            // DRIVE
            robot.driveWithGamepad(gamepad1)

            // CUBES INTAKE
            robot.intakeWithGamepad(gamepad2)

            // CUBES LIFT
            robot.liftWithGamepad(gamepad2)

            // CUBES DROP
            robot.dropWithGamepad(gamepad2)
        }
    }
}
