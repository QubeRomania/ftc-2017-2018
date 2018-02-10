package ro.cnmv.qube.core

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import org.firstinspires.ftc.robotcore.external.Telemetry

interface DriveMotors: OpModeAccess {
    companion object {
        const val MAX_POWER = 0.78

        const val FRONT_LEFT = 1.0
        const val FRONT_RIGHT = 1.0
        const val BACK_LEFT = 1.0
        const val BACK_RIGHT = 1.0
    }

    val frontLeft: DcMotor
    val frontRight: DcMotor

    val backLeft: DcMotor
    val backRight: DcMotor

    fun resetEncoders() {
        frontLeft.mode = RunMode.STOP_AND_RESET_ENCODER
        frontRight.mode = RunMode.STOP_AND_RESET_ENCODER
    }

    fun setPower(frontLeft: Double, frontRight: Double, backLeft: Double, backRight: Double) {
        this.frontLeft.power = frontLeft * FRONT_LEFT * MAX_POWER
        this.frontRight.power = frontRight * FRONT_RIGHT * MAX_POWER
        this.backLeft.power = backLeft * BACK_LEFT * MAX_POWER
        this.backRight.power = backRight * BACK_RIGHT * MAX_POWER

        //printPower()
    }

    fun stopMotors() {
        setPower(0.0, 0.0, 0.0, 0.0)
    }

    fun printPower() {
        tele.addData("Front left", frontLeft.power.toString())
        tele.addData("Front right", frontRight.power.toString())
        tele.addData("Back left", backLeft.power.toString())
        tele.addData("Back right", backRight.power.toString())
    }

    fun printPositions() {
        tele.addData("Front left pos", frontLeft.currentPosition)
        tele.addData("Front right pos", frontRight.currentPosition)
        tele.addData("Back left pos", backLeft.currentPosition)
        tele.addData("Back right pos", backRight.currentPosition)
    }

    private fun motors(): Array<DcMotor> = arrayOf(frontLeft, frontRight, backLeft, backRight)
}
