// RUN_PIPELINE_TILL: FRONTEND
enum class <!REDECLARATION, REDECLARATION!>A<!> {
    <!REDECLARATION!>name<!>,
    <!REDECLARATION!>ordinal<!>,
    <!DECLARATION_OF_ENUM_ENTRY_ENTRIES_ERROR!>entries<!>,
}

/* GENERATED_FIR_TAGS: enumDeclaration, enumEntry */
