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

    suspend fun run () : List<TestResult> {
        var results = tests.map{ it.run() }

        return results.awaitAll()
    }
}

