// RUN_PIPELINE_TILL: FRONTEND
// ISSUE: KT-65337

inline fun bar(s: () -> Unit) {
    (<!UNDERSCORE_IS_RESERVED!>_<!>@ s)()
}

/* GENERATED_FIR_TAGS: functionDeclaration, functionalType, inline */
