package pack

annotation class Anno(val value: Int)

open class SuperClass {
    companion object {
        const val CONST = 1
    }
}

class TopLevelClass(val i: Int) : SuperClass() {
    val CONST = "str"

    <expr>@Anno(CONST)</expr>
    val <T> T.foo get() = 0

    companion object {
        val STR = "str"
    }
}
