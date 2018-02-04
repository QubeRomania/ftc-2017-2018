package ro.cnmv.qube.drive

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import ro.cnmv.qube.core.RobotOpMode

@TeleOp(name = "Complete Drive", group = "Drive")
class DriveOpMode: RobotOpMode() {
    override fun runOpMode() {
        robot.setEncoders(false)

        waitForStart()

        while (opModeIsActive()) {
            // DRIVE
            robot.driveWithGamepad(gamepad1)

            // CUBES INTAKE
            robot.intakeWithGamepad(gamepad2)

            // CUBES DROP
            robot.dropWithGamepad(gamepad2)

            // CUBES OPEN
            robot.openWithGamepad(gamepad2)
        }
    }
}