// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// FILE: weatherForecast/Weather.java
package weatherForecast;

public class Weather {
    public void test() {}
}

// FILE: a/A.java
package a;

import weatherForecast.*;
import weatherForecast.*;

public class A {
    public Weather getWeather() { return null; }
}

// FILE: a.kt
package a

fun test() = A().getWeather().test()

/* GENERATED_FIR_TAGS: flexibleType, functionDeclaration, javaFunction, javaType */
