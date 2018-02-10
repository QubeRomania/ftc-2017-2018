package ro.cnmv.qube.pid

class DistancePid: Pid {
    override val p = 0.1
    override val i = 0.05
    override val d = 0.1
}
