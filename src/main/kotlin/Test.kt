package me.jonahss

class Test(val name : String, vararg tags : Tag, val script : (test : Test) -> TestResult) {
    var result : TestResult = TestResult(name)
    var tags = tags


    fun pass () {
        result.state = TestResultStatus.PASS
    }

    fun fail () {
        result.state = TestResultStatus.FAIL
    }

    fun run () : TestResult {
        return script(this)
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

    fun run () : HashSet<TestResult> {
        val results = HashSet<TestResult>()
        for (test in tests) {
            results.add(test.run())
        }
        return results
    }
}

