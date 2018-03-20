package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.util.ElapsedTime
import ro.cnmv.qube.core.RobotOpMode

@Autonomous(name = "Auto Strafing Test", group = "Tests")
class AutoStrafingTest: RobotOpMode() {
    private val targetDistance = 20.0

    var lastLeftState =  false
    var lastRightState = false
    private var leftCount = 0
    private var rightCount = 0


    override fun runOpMode() {
        calibrateGyro()

        waitForStart()

        val leftDigitalSensor = hardwareMap.digitalChannel["leftDigitalSensor"]
        val rightDigitalSensor = hardwareMap.digitalChannel["rightDigitalSensor"]
        val timer: ElapsedTime

        leftDigitalSensor.mode = DigitalChannel.Mode.INPUT
        rightDigitalSensor.mode = DigitalChannel.Mode.INPUT

        var lastAngleError = 0.0
        var lastDistanceError = 0.0

        while (opModeIsActive()){
            if(leftDigitalSensor.state && !lastLeftState){
                leftCount++
            }

            if(rightDigitalSensor.state && !lastRightState){
                rightCount++
            }

            lastLeftState = leftDigitalSensor.state
            lastRightState = rightDigitalSensor.state

            val basePower = when {
                gamepad1.x -> 0.8
                gamepad1.b -> -0.8
                else -> 0.0
            }

            val motorCorrection = {
                val error = -robot.heading.toDouble()

                val P = 0.05
                val I = 0.01
                val D = 0.2
                val pid = P * error + I * (error + lastAngleError) + D * (error - lastAngleError)

                lastAngleError = error

                pid
            }()

            /*
            val correction = {
                val currentDistance = robot.backDistance
                val error = targetDistance - currentDistance

                val P = 0.025
                val I = 0.005
                val D = 0.02

                val pid = P * error + I * (error + lastDistanceError) + D * (error - lastDistanceError)

                lastDistanceError = error

                pid
            }()
            */
            val correction = basePower / -8.0
            robot.setPower(
                    -basePower - motorCorrection + correction ,
                    basePower + motorCorrection + correction,
                    basePower - motorCorrection + correction,
                    -basePower + motorCorrection + correction)



            telemetry.addData("Angle", robot.heading)
            telemetry.addData("Correction", motorCorrection)
            telemetry.addData("LeftCount", leftCount)
            telemetry.addData("RightCount", rightCount)

            telemetry.update()
        }
    }
}
