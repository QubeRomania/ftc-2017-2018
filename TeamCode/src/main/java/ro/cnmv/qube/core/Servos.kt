package ro.cnmv.qube.core

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

interface Servos {
    val leftLiftServo: CRServo
    val rightLiftServo: CRServo
    val leftDropServo: CRServo
    val rightDropServo: CRServo
    val jewServo: Servo
}
