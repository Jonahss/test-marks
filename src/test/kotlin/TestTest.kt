package me.jonahss

import java.util.Arrays.asList

enum class MyTags : Tag {
    SANITY,
    REGRESSION,
    SMOKE,
    POSTDEPLOY
}

enum class YourTags : Tag {
    PERFORMANCE,
    SUPERFLAKEY
}

enum class Author : Tag {
    MIKE,
    CHUCK
}

fun main(args : Array<String>) {

    var testA = Test("testA", MyTags.SANITY, YourTags.SUPERFLAKEY, Author.CHUCK, script = fun(t) : TestResult {
        // user.signin
        // assert user is logged in
        // test code here
        // driver.getElementbyId....

        t.pass()

        return t.result
    })


    var testB = Test("testB", script = fun(t) : TestResult {
        // test code here

        t.fail()

        return t.result
    })

    val testAResult = testA.run()
    val testBResult = testB.run()

    println(testAResult)
    println(testBResult)


    // or

    val results = asList(testA.run(), testB.run())

    println(results)

    // how about a suite?

    val smokeTests = TestSuite("Smoke Tests", testA, testB)

    val smokeTestResults = smokeTests.run()

    println(smokeTestResults)

    /*
    var sanityTests = getTestsWithTag(MyTags.SANITY)

    var smokeTests = getTestsWithTag("smoke")
    getTestsUsingSQL("where createdAt < Today")
    mikesTests = getAnyTestWithTag(MyTags.value())
    smokeTests.run()

     */
}