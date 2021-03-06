package ro.cnmv.qube.autonomy

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark
import ro.cnmv.qube.core.RobotOpMode
import ro.cnmv.qube.systems.Jewel

abstract class AutonomyBase: RobotOpMode() {
    abstract val ourColor: Jewel.Color
    abstract val vuforiaTimeout: Long

    override fun runOpMode() {
        if (hardwareMap == null)
            throw RuntimeException("Hardware map has not been initialized!")

        postInit()

        waitForStart()

        if (!opModeIsActive()) {
            robot.stop()
            return
        }

        readVuMark()

        robot.dropJewel(ourColor)

        postStart()

        robot.stop()
    }

    private fun postInit() {
        robot.resetEncoders()

        robot.initVuforia()

        calibrateGyro()

        robot.leftDropServo.position = 0.32
        robot.rightDropServo.position = 0.32
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
        while (opModeIsActive() && vuMark == RelicRecoveryVuMark.UNKNOWN && timer.milliseconds() < vuforiaTimeout) {
            vuMark = vuforia.vuMark

            waitForMs(50)
        }

        setStatus("VuMark is $vuMark")
        update()

        waitForMs(500)

        vuforia.deactivate()
    }

    ///
    protected fun RelicRecoveryVuMark.column() = when (this) {
        RelicRecoveryVuMark.LEFT -> 3
        RelicRecoveryVuMark.RIGHT -> 1
        else -> 2
    }

    protected fun dropCube() {
        setStatus("Dropping cube")
        update()

        robot.driveDistance(5.0, robot.heading.toDouble())

        waitForMs(200)
        robot.dropCubesAuto(true)
        waitForMs(1200)


        setStatus("Pushing cube in crypto box")
        update()

        robot.driveDistance(5.0, robot.heading.toDouble())

        robot.driveTime(2000, -0.4)

        robot.driveDistance(3.0, robot.heading.toDouble())

        robot.dropCubesAuto(false)
    }

}
