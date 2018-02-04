package ro.cnmv.qube.core

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import ro.cnmv.qube.systems.Vuforia

/// Base class for all of our robot's OpModes.
abstract class RobotOpMode: LinearOpMode() {
    /// The robot's hardware.
    protected val robot: Robot by lazy {
        if (hardwareMap == null)
            throw RuntimeException("Robot not yet initialized!")

        Robot(hardwareMap)
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
        while (opModeIsActive() && gyro.isCalibrating) {
            setStatus("Calibrating gyro. Please wait...")
            update()

            sleep(100)
        }
    }

    protected fun waitForMs(millis: Long) {
        val timer = ElapsedTime()
        while (opModeIsActive() && timer.milliseconds() <= millis)
            ;
    }
}
