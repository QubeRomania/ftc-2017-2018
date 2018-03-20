package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import ro.cnmv.qube.core.RobotOpMode

abstract class AutonomyBase: RobotOpMode() {
    override fun runOpMode() {
        if (hardwareMap == null)
            throw RuntimeException("Hardware map has not been initialized!")

        postInit()

        waitForStart()

        if (!opModeIsActive()) {
            robot.stop()
            return
        }

        postStart()
    }

    private fun postInit() {
        robot.resetEncoders()

        robot.initVuforia()

        calibrateGyro()
    }

    protected abstract fun postStart()

    /// Detected VuMark.
    protected var vuMark = RelicRecoveryVuMark.UNKNOWN

    protected fun readVuMark() {
        setStatus("Reading VuMark")
        update()

        val vuforia = robot.vuforia

        vuforia.activate()
        waitForMs(200)

        val timer = ElapsedTime()
        while (opModeIsActive() && vuMark == RelicRecoveryVuMark.UNKNOWN && timer.milliseconds() < 1000) {
            vuMark = vuforia.vuMark

            waitForMs(50)
        }

        setStatus("VuMark is $vuMark")
        update()

        waitForMs(500)

        vuforia.deactivate()
    }

    ///
    private fun RelicRecoveryVuMark.column() = when (this) {
        RelicRecoveryVuMark.LEFT -> 1
        RelicRecoveryVuMark.RIGHT -> 3
        else -> 2
    }

    protected fun dropCube() {
        setStatus("Dropping cube")
        update()

        robot.dropCubesAuto(true)
        waitForMs(900)
        robot.dropCubesAuto(false)

        waitForMs(1000)

        setStatus("Pushing cube in crypto box")
        update()

        robot.driveDistance(5.0, robot.heading.toDouble())

        robot.driveTime(2000, -0.4)

        robot.driveDistance(3.0, robot.heading.toDouble())
    }
}
