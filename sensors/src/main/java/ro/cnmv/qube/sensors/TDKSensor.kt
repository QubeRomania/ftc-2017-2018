@file:JvmName("TDKSensor")

package ro.cnmv.qube.sensors

import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.util.TypeConversion
import java.nio.ByteOrder

class TDKSensor(synch: I2cDeviceSynch): I2cDeviceSynchDevice<I2cDeviceSynch>(synch, true) {
    companion object {
        val I2C_ADDRESS = I2cAddr(0b1101000)
        const val GYRO_RESOLUTION = 250.0 / 32768.0
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

        // Configure gyroscope.
        val gyroConfig = 0
        deviceClient.write8(Register.GYRO_CONFIG.address, gyroConfig)

        return true
    }

    override fun getManufacturer() = HardwareDevice.Manufacturer.Other
    override fun getDeviceName(): String = "TDK MPU 9250"

    enum class Register(val address: Int) {
        GYRO_CONFIG(27),
        FIFO_ENABLE(35),
        TEMPERATURE(65),

        GYRO_X(67),
        GYRO_Y(69),
        GYRO_Z(71);

        companion object {
            const val FIRST_REGISTER = 0x3B
            const val LAST_REGISTER = 0x48
        }
    }

    private fun readShort(r: Register) = TypeConversion.byteArrayToShort(deviceClient.read(r.address, 2), ByteOrder.BIG_ENDIAN)

    fun getTemperature(): Double {
        val tempRaw = deviceClient.read(Register.TEMPERATURE.address, 2)
        val tempOut = TypeConversion.byteArrayToShort(tempRaw, ByteOrder.BIG_ENDIAN)

        return (tempOut - 21.0) / 333.87 + 21.0
    }

    fun rawX(): Double {
        return readShort(Register.GYRO_X) * GYRO_RESOLUTION
    }
}
