package ro.cnmv.qube.pid

/*
 * Copyright (c) 2018 FTC team 4634 FROGbots
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.qualcomm.robotcore.hardware.PIDCoefficients
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.ByteBuffer

class RemotePid {
    var pid = PIDCoefficients(0.0, 0.0, 0.0)

    private var backgroundThread: Thread? = null

    fun beginListening() {
        backgroundThread = Thread({ listen() })

        backgroundThread!!.start()
    }

    fun shutdown() {
        backgroundThread!!.interrupt()
    }

    private fun listen() {
        try {
            val port = 8087
            val serverSocket = DatagramSocket(port)
            val packet = ByteArray(24)

            System.out.printf("Listening on udp:%s:%d%n", InetAddress.getLocalHost().hostAddress, port)
            val receivePacket = DatagramPacket(packet, packet.size)

            while (!Thread.currentThread().isInterrupted) {
                serverSocket.receive(receivePacket)

                val pVal = ByteArray(8)
                val iVal = ByteArray(8)
                val dVal = ByteArray(8)

                System.arraycopy(packet, 0, pVal, 0, 8)
                System.arraycopy(packet, 8, iVal, 0, 8)
                System.arraycopy(packet, 16, dVal, 0, 8)

                fun toDouble(bytes: ByteArray): Double {
                    return ByteBuffer.wrap(bytes).double
                }

                pid.p = toDouble(pVal)
                pid.i = toDouble(iVal)
                pid.d = toDouble(dVal)
            }

            serverSocket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
