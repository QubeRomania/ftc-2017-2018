package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Voltage Test", group = "Tests")
class VoltageTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()

        while (opModeIsActive()) {
            telemetry.addData("Voltage", "%.2f", robot.voltage)
            telemetry.update()

            sleep(100)
        }
    }
}
