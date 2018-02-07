package ro.cnmv.qube.core

import org.firstinspires.ftc.robotcore.external.Telemetry

interface OpModeAccess {
    val opModeActive: Boolean
    val tele: Telemetry
}
