package me.jonahss

import org.junit.Assert.assertEquals
import org.junit.Test

class MyLibraryTest {
    @Test fun testMyLanguage() {
        assertEquals("Kotlin", MyLibrary().kotlinLanguage().name)
        assertEquals(10, MyLibrary().kotlinLanguage().hotness)
    }

    @Test fun testCoroutines() {
        val sum = MyLibrary().doit()
        assertEquals(500_500, sum)
    }
}
