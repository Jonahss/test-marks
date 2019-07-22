package me.jonahss.demos

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.jonahss.Test
import me.jonahss.TestResult
import me.jonahss.TestSuite
import kotlin.random.Random

fun RandomizedTimeTest(name : String) : Test  {

    var delayMs = Random.nextLong(100, 3000)

    return Test(name = name, testScript = fun(t) : TestResult {
        runBlocking {
           delay(delayMs)
        }
        t.pass()
        return t.result
    })
}

fun main () {
    var numTests = 100
    val tests = Array(numTests) { i -> RandomizedTimeTest("test $i") }

    val suite = TestSuite("$numTests tests which delay random amounts of time",  *tests)
    suite.onTestStart = { println("starting ${it.name}") }
    suite.onTestFinish = { println("test ${it.testName} ${it.state}") }

    runBlocking{
        suite.run()
    }
}


