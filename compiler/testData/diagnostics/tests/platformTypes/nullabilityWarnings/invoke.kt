// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// FILE: J.java

import org.jetbrains.annotations.*;

public class J {
    public interface Invoke {
        void invoke();
    }

    @NotNull
    public static Invoke staticNN;
    @Nullable
    public static Invoke staticN;
    public static Invoke staticJ;
}

// FILE: k.kt

fun test() {
    J.staticNN()
    J.<!UNSAFE_IMPLICIT_INVOKE_CALL!>staticN<!>()
    J.staticJ()
}

/* GENERATED_FIR_TAGS: flexibleType, functionDeclaration, javaProperty, javaType, nullableType */
