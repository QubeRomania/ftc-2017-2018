package ro.cnmv.qube.pid

class DistancePid: Pid {
    override val p = 0.1
    override val i = 0.02
    override val d = 0.1
}
