// RUN_PIPELINE_TILL: BACKEND
// FILE: A.java

public class A {
    public int x;

    public String getX() {
        return "";
    }
}

// FILE: main.kt

fun test(a: A) {
    val int = a.x // <- should be int
    val string = a.getX()
}

/* GENERATED_FIR_TAGS: flexibleType, functionDeclaration, javaFunction, javaProperty, javaType, localProperty,
propertyDeclaration */
