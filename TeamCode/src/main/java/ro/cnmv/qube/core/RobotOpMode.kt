package ro.cnmv.qube.core

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.Telemetry

/// Base class for all of our robot's OpModes.
abstract class RobotOpMode: LinearOpMode(), OpModeAccess {
    override val tele: Telemetry = telemetry
    override val opModeActive
        get() = opModeIsActive()

    override fun waitMillis(millis: Long) {
        waitForMs(millis)
    }

    /// The robot's hardware.
    protected val robot by lazy {
        if (hardwareMap == null)
            throw RuntimeException("Robot not yet initialized!")

        Robot(this)
    }

    protected fun setStatus(status: String) {
        telemetry.addData("Status", status)
    }

    protected fun update() {
        telemetry.update()
    }

    protected fun calibrateGyro() {
        val gyro = robot.gyro

        gyro.calibrate()

        while (!isStopRequested && gyro.isCalibrating) {
            tele.addData("Calibrating", "Please wait!")
            tele.update()
            sleep(50)
        }

        gyro.resetZAxisIntegrator()

        tele.log().clear()
        tele.log().add("Gyro calibrated")
        tele.clear()
        tele.update()
    }

    protected fun waitForMs(millis: Long) {
        val timer = ElapsedTime()
        while (opModeIsActive() && timer.milliseconds() <= millis)
            // Allow other threads to run.
            idle()
    }
}
