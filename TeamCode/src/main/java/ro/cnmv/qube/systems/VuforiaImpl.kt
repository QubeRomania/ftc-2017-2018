package ro.cnmv.qube.systems

import android.content.Context
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark

class VuforiaImpl: Vuforia {
    companion object {
        /// License key for VuforiaImpl.
        private const val LICENSE_KEY = "AVVjWu3/////AAAAGdsr5gBC+UssmiGXoEvaSklmeAVfXdWZjUNR2gbOBgC0kp6Do6J3i1FIgwwlpWTgUgGtotRa+mZH5uJSNVWk5T8UMU7p38lmYOGRFOD5pXXJen9S2ltuwpR4C1QWHxQTHDxP6ubRgdEHj1K2Qhpw82ZPllmN7ZsAUacC7/AyPakH1L2O8aw7KBQr98D8HMPIC3GLuJQ2ZzWCNcll+LvP6YmIZcwH0U6CtwgDUTsVpJvcR2QFvFhbh972AAMZ2b7vuNmubrDHizY11qWico+9ovgN5ADLi2IztLhNZG3MLQLrsOa0wAKt4yMKPk2uV8JCTTVkZeqBhKaIPZMRTQuP3Lcvrco5PAi6jVRZ4WhGXhHc";
    }

    private val vuforiaLocalizer: VuforiaLocalizer
    private val trackables: VuforiaTrackables
    private val relicTemplate: VuforiaTrackable

    constructor(context: Context) {
        /* TODO: decide whether to enable camera monitoring.
        // Obtain the ID of the camera view.
        val cameraMonitorViewId = context.resources.getIdentifier(
            "cameraMonitorViewId",
            "id",
            context.packageName
        )
        */

        val cameraMonitorViewId = 0

        val parameters = VuforiaLocalizer.Parameters(cameraMonitorViewId)

        parameters.vuforiaLicenseKey = LICENSE_KEY
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK

        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters)

        // Load all trackables available for this season.
        trackables = vuforiaLocalizer.loadTrackablesFromAsset("RelicVuMark")

        relicTemplate = trackables[0]
    }

    override fun activate() {
        trackables.activate()
    }

    override fun deactivate() {
        trackables.deactivate()
    }

    override val vuMark: RelicRecoveryVuMark
        get() = RelicRecoveryVuMark.from(relicTemplate)
}
