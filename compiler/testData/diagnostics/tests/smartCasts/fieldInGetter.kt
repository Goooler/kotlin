// RUN_PIPELINE_TILL: BACKEND
val x: Int? = 0
    get() {
        if (field != null) return <!DEBUG_INFO_SMARTCAST!>field<!>.hashCode()
        return null
    }

/* GENERATED_FIR_TAGS: equalityExpression, getter, ifExpression, integerLiteral, nullableType, propertyDeclaration,
smartcast */
