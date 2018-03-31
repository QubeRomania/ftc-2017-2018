package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import ro.cnmv.qube.sensors.TDKSensor

@Autonomous(name = "TDK Gyro Test", group = "Tests")
class TDKGyroTest: LinearOpMode() {
    override fun runOpMode() {
        val device = hardwareMap.i2cDeviceSynch["newGyro"]

        val gyro = TDKSensor(device)

        waitForStart()

        while (opModeIsActive()) {
            telemetry.addData("Temperature", gyro.getTemperature())
            telemetry.addData("Raw X", gyro.rawX())
            telemetry.update()
        }
    }
}
