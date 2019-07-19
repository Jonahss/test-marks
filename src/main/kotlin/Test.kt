package me.jonahss

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class Test(val name : String, vararg tags : Tag, val script : (test : Test) -> TestResult) {
    var result : TestResult = TestResult(name)
    var tags = tags


    fun pass () {
        result.state = TestResultStatus.PASS
    }

    fun fail () {
        result.state = TestResultStatus.FAIL
    }

    fun run () : Deferred<TestResult> {
        val self = this
        // TODO set this.result, make it a deferred
        return GlobalScope.async {
            script(self)
        }
    }
}

class TestResult(val testName : String) {
    // TODO probably include the run number
    // TODO get test run time

    var state : TestResultStatus = TestResultStatus.INCOMPLETE

    override fun toString(): String {
        return "$testName - $state"
    }
}

enum class TestResultStatus {
    PASS,
    FAIL,
    SKIP,
    INCOMPLETE
}


interface Tag {

}

class TestSuite(val name : String, vararg tests : Test) {

    val tests = tests
    var onTestStart : ((test : Test) -> Unit)? = null
    var onTestFinish : ((result : TestResult) -> Unit)? = null

    // TODO set this.results
    // TODO can TestSuite just extend a Collection?
    // TODO can we have a Test interface , so that a suite can be composed of a mixture of tests and other suites?

    suspend fun run () : List<TestResult> {
        var results = tests.map{
            GlobalScope.async {
                onTestStart?.invoke(it)
                val result = it.run().await()
                onTestFinish?.invoke(result)
                result
            }
        }

        return results.awaitAll()
    }
}

