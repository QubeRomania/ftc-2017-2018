package ro.cnmv.qube.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.I2cAddr
import com.qualcomm.robotcore.hardware.I2cDeviceSynch
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice
import com.qualcomm.robotcore.util.TypeConversion
import java.nio.ByteOrder

@Autonomous(name = "TDK Gyro Test", group = "Tests")
class TDKGyroTest: LinearOpMode() {
    override fun runOpMode() {
        val device = hardwareMap.i2cDeviceSynch["newGyro"]

        val gyro = TdkImu(device)

        waitForStart()

        while (opModeIsActive()) {
            telemetry.addData("Temperature", gyro.getTemperature())
            telemetry.update()
        }
    }
}

class TdkImu(synch: I2cDeviceSynch): I2cDeviceSynchDevice<I2cDeviceSynch>(synch, true) {
    companion object {
        val I2C_ADDRESS = I2cAddr(0b1101000)
    }

    init {
        deviceClient.i2cAddress = I2C_ADDRESS

        val first = Register.FIRST_REGISTER
        val registerCount = Register.LAST_REGISTER - Register.FIRST_REGISTER + 1

        deviceClient.readWindow = I2cDeviceSynch.ReadWindow(first, registerCount, I2cDeviceSynch.ReadMode.REPEAT)

        registerArmingStateCallback(false)
        engage()
    }

    override fun doInitialize(): Boolean {
        /* TODO: fix FIFO.
        // Enable FIFO for temperature
        val fifoFlags = 1 shl 8
        deviceClient.write(Register.FIFO_ENABLE.address, byteArrayOf(fifoFlags.toByte()))
        */

        return true
    }

    override fun getManufacturer() = HardwareDevice.Manufacturer.Other
    override fun getDeviceName(): String = "TDK MPU 9250"

    enum class Register(val address: Int) {
        FIFO_ENABLE(35),
        TEMPERATURE(0x41);

        companion object {
            const val FIRST_REGISTER = 0x3B
            const val LAST_REGISTER = 0x48
        }
    }

    fun getTemperature(): Double {
        val tempRaw = deviceClient.read(Register.TEMPERATURE.address, 2)
        val tempOut = TypeConversion.byteArrayToShort(tempRaw, ByteOrder.BIG_ENDIAN)

        return (tempOut - 21.0) / 333.87 + 21.0
    }
}
