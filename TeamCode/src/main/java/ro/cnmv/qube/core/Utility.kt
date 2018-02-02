package ro.cnmv.qube.core

fun clamp(value: Double, min: Double, max: Double) =
    when {
        value < min -> min
        value > max -> max
        else -> value
    }