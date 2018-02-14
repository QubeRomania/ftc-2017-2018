package ro.cnmv.qube.autonomy

import ro.cnmv.qube.core.RobotOpMode

abstract class AutonomyBase: RobotOpMode() {
    override fun runOpMode() {
        postInit()

        waitForStart()

        if (!opModeIsActive())
            return

        postStart()
    }

    private fun postInit() {
        robot.initVuforia()

        robot.resetEncoders()

        calibrateGyro()
    }

    protected abstract fun postStart()
}
