package ro.cnmv.qube.core

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import org.firstinspires.ftc.robotcore.external.Telemetry

const val MAX_POWER = 0.78

const val FRONT_LEFT = 1.0
const val FRONT_RIGHT = 1.0
const val BACK_LEFT = 1.0
const val BACK_RIGHT = 1.0

interface Motors: Telemetry {
    val frontLeft: DcMotor
    val frontRight: DcMotor

    val backLeft: DcMotor
    val backRight: DcMotor

    val intakeLeft: DcMotor
    val intakeRight: DcMotor

    val intakeOpen: DcMotor

    fun setEncoders(useEncoders: Boolean) {
        if (useEncoders) {
            for (motor in motors()) {
                motor.mode = RunMode.RUN_USING_ENCODER
            }
            resetEncoders()
        } else {
            for (motor in motors()) {
                motor.mode = RunMode.RUN_WITHOUT_ENCODER
            }
        }
    }

    fun resetEncoders() {
        for (motor in motors()) {
            motor.mode = RunMode.STOP_AND_RESET_ENCODER
        }
    }

    fun setPower(frontLeft: Double, frontRight: Double, backLeft: Double, backRight: Double) {
        this.frontLeft.power = frontLeft * FRONT_LEFT * MAX_POWER
        this.frontRight.power = frontRight * FRONT_RIGHT * MAX_POWER
        this.backLeft.power = backLeft * BACK_LEFT * MAX_POWER
        this.backRight.power = backRight * BACK_RIGHT * MAX_POWER
    }

    fun stop() {
        setPower(0.0, 0.0, 0.0, 0.0)
    }

    fun printPower() {
        addData("Front left", frontLeft.power.toString())
        addData("Front right", frontRight.power.toString())
        addData("Back left", backLeft.power.toString())
        addData("Back right", backRight.power.toString())
    }

    private fun motors(): Array<DcMotor> = arrayOf(frontLeft, frontRight, backLeft, backRight)
}
