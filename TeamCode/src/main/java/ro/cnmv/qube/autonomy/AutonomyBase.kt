package ro.cnmv.qube.autonomy

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
}
