// RUN_PIPELINE_TILL: BACKEND
object X

class Y {
    fun f(op: X.() -> Unit) {
        X.op()

        val x = X
        x.op()
    }
}
