package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark

import ro.cnmv.qube.core.RobotOpMode

/// Tests VuforiaImpl's capability to detect the pictograph's pattern.
@Autonomous(name = "Vuforia Test", group = "Tests")
class VuforiaTest: RobotOpMode() {
    override fun runOpMode() {
        waitForStart()

        val vuforia = robot.vuforia

        vuforia.activate()

        while (opModeIsActive()) {
            val vuMarkType = when (vuforia.vuMark) {
                RelicRecoveryVuMark.CENTER -> "Center"
                RelicRecoveryVuMark.UNKNOWN -> "Unknown"
                RelicRecoveryVuMark.LEFT -> "Left"
                RelicRecoveryVuMark.RIGHT -> "Right"
            }

            telemetry.addData("Detected VuMark:", vuMarkType)
            telemetry.update()

            // Add a small delay to the loop.
            sleep(100)
        }

        vuforia.deactivate()
    }
}
