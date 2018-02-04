package ro.cnmv.qube.systems

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark

interface Vuforia {
    /// Activates the localizer.
    fun activate();

    /// Deactivates the localizer.
    fun deactivate();

    /// The currently detected VuMark.
    val vuMark: RelicRecoveryVuMark
}
