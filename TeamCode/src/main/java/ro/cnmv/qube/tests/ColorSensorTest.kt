package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Color Sensor Test", group = "Test")
class ColorSensorTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()

        while (opModeIsActive()) {
            val color = robot.colorSensor.normalizedColors

            tele.addData("Red", color.red)
            tele.addData("Blue", color.blue)
            tele.update()
        }
    }
}
