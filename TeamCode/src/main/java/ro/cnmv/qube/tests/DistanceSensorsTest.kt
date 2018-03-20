package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DigitalChannel

@Autonomous(name = "DistanceSensorsTests", group = "Tests")
class DistanceSensorsTest: LinearOpMode() {
    override fun runOpMode(){

        val leftAnalogSensor = hardwareMap.analogInput["leftAnalogSensor"]
        val rightAnalogSensor = hardwareMap.analogInput["rightAnalogSensor"]

        val leftDigitalSensor = hardwareMap.digitalChannel["leftDigitalSensor"]
        val rightDigitalSensor = hardwareMap.digitalChannel["rightDigitalSensor"]

        leftDigitalSensor.mode = DigitalChannel.Mode.INPUT
        rightDigitalSensor.mode = DigitalChannel.Mode.INPUT

        waitForStart()

        while (opModeIsActive()) {
            telemetry.addData("Left Digital sensor", leftDigitalSensor.state)
            telemetry.addData("Right Digital sensor", rightDigitalSensor.state)
            telemetry.addData("Left Analog sensor", leftAnalogSensor.voltage)
            telemetry.addData("Right Analog sensor", rightAnalogSensor.voltage)
            telemetry.update()
        }
    }
}
