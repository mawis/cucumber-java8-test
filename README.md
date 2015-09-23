I wrote the code in this repository to experiment with the Java8 dialect of
Cucumber-JVM.


Running the project
-------------------

To run the demonstration you just have to enter "mvn clean verify" in the
project root directory.


Running on Oracle JDK8
----------------------

Everything is working fine on Oracle's JDK8. The tests fail (as expected) with a
PendingException:

    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running soy.wimmer.CucumberIT

    1 Scenarios (1 pending)
    3 Steps (2 skipped, 1 pending)
    0m0,231s

    cucumber.api.PendingException: TODO: implement me
    	at soy.wimmer.CucumberStepdefs.lambda$new$0(CucumberStepdefs.java:10)
    	at ✽.Given I have some dummy code(cucumber/cucumber-java8.feature:7)

    Tests run: 5, Failures: 0, Errors: 0, Skipped: 4, Time elapsed: 0.518 sec - in soy.wimmer.CucumberIT

    Results :

    Tests run: 4, Failures: 0, Errors: 0, Skipped: 3


Running on OpenJDK 1.8.0
------------------------

When I run the same project on OpenJDK 1.8.0 it does not behave as expected.
Running the test fails with an internal problem in Cucumber-Java8:

    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running soy.wimmer.CucumberIT

    0 Scenarios
    0 Steps
    0m0.000s

    Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.056 sec <<< FAILURE! - in soy.wimmer.CucumberIT
    Feature: Cucumber with Java8  Time elapsed: 0.047 sec  <<< ERROR!
    cucumber.runtime.CucumberException: Failed to instantiate class soy.wimmer.CucumberStepdefs
    […]
    Caused by: java.lang.reflect.InvocationTargetException: null
    […]
    Caused by: cucumber.runtime.CucumberException: java.lang.IllegalArgumentException: Wrong type at constant pool index
    […]
    Caused by: java.lang.IllegalArgumentException: Wrong type at constant pool index
    	at sun.reflect.ConstantPool.getMemberRefInfoAt0(Native Method)
    	at sun.reflect.ConstantPool.getMemberRefInfoAt(ConstantPool.java:47)
    	at cucumber.runtime.java8.ConstantPoolTypeIntrospector.getTypeString(ConstantPoolTypeIntrospector.java:37)
    	at cucumber.runtime.java8.ConstantPoolTypeIntrospector.getGenericTypes(ConstantPoolTypeIntrospector.java:27)
    	at cucumber.runtime.java.Java8StepDefinition.<init>(Java8StepDefinition.java:45)
    	at cucumber.runtime.java.JavaBackend.addStepDefinition(JavaBackend.java:162)
    	at cucumber.api.java8.En.Given(En.java:190)
    	at soy.wimmer.CucumberStepdefs.<init>(CucumberStepdefs.java:8)
    […]


    Results :

    Tests in error:
      Failed to instantiate class soy.wimmer.CucumberStepdefs

    Tests run: 1, Failures: 0, Errors: 1, Skipped: 0

This is caused by ![this line of code](https://github.com/cucumber/cucumber-jvm/blob/master/java8/src/main/java/cucumber/runtime/java8/ConstantPoolTypeIntrospector.java#L37).

Accessing a fixed position (second last element) in the constant pool seems to
be the problem for the compatibility problem. There is no guaranty of any
particular order of the items in there, and it seems that OpenJDK has a
different order than Oracle's JDK.


Pointers
--------

I had great help from user Holger at Stackoverflow (http://stackoverflow.com/users/2711488/holger) to understand this problem.

I posted this problem on Stackoverflow: http://stackoverflow.com/questions/32728342/always-getting-exception-wrong-type-at-constant-pool-index-with-cucumber-java8

I have opened an issue report here:
https://github.com/cucumber/cucumber-jvm/issues/912
