package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous(name = "LED Test", group = "Tests")
class LedTest: LinearOpMode() {
    override fun runOpMode() {
        val led = hardwareMap.led["leftLed"]

        waitForStart()

        while (opModeIsActive()) {
            if (gamepad1.x)
                led.enable(true)
            else
                led.enable(false)
        }

        led.enable(false)
    }
}
