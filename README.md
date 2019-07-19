## Why Are Tests Methods? And Suites Classes? A Test should be an Object

jonah [5:41 PM]
funny u mention it, im actually working on a kotlin test framework to replace junit/testng

Noam [5:42 PM]
Hah
What sort of framework

jonah [5:45 PM]
based on a couple key premises
first of all: a javascript test runner called Ava impressed me with a great feature of being parallel by default. all tests run in parallel.
too often with junit and the like, teams dont parallelize their tests until its too late and discover that their tests all depend on each other
so
1: parallel by default

jonah [5:52 PM]
second, tests in java always felt weird shimmed into the OOP structure. why are tests methods, and collections of tests (called suites in other frameworks) classes? doesnt make much sense, because everybody thinks of their tests as being objects, and collections of tests should just be collections.

so my intent is to make classes which extend a base Test class, but your tests are instantiated instances of a Test. with methods like ‘run()’ which return the test result.

i have a theory that the reason it is the way it is now is because the designers wanted an easy way to parse code files and find tests, and they could do this using the IDE’s parser.

of course, in order to work the way i imagine, test objects need to live in some record. i suggest a database (or flat file, s3 bucket, etc) which stores a representation of your test objects. it can be populated at test runtime, and the test name is primary key. this gives you the benefit of collecting historical run data and being able to move a test between files and not lose its identity
so:
1: parallel by default
2: tests as objects, stored in a shared data store
thirdly, theres no need now for a 1:many mapping between suites and tests. you should be able to make any number of collections of tests to run in different situations
fourth, and final, the parallelism and suites makes it difficult to manage test preconditions.
so my goal is to have functions which you can declare as a prerequisite for a test. for example, you indicate that your test requires a user with a shopping cart with one item in it. the test system when in a planning stage before running tests (but after knowing what tests you want to run) analyzes the prerequisites and assembles a tree of tests which satisfy your prerequisites. building the minimal set of tests with maximum parallelizstion
so in our example, it would first run the test which creates a user, and the test which creates an item in parallel. then run the test which puts an item in a cart, then it will run the test which had those things as a prerequisite
how do you declare what state your tests satisfy after completion? such as creating a new user? well your assertions could provide that information
dont know exactly what that looks like, but im just gonna start writing it and experiment