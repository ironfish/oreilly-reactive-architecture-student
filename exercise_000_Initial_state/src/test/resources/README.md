initial-state

# Exercise 0 > Initial State

Before we begin coding, we want to verify that the initial state is working as expected. From your `sbt` session: 

- Use the `run` command to check the main class `CoffeeHouseApp` boots up as expected. You should see the following:

```scala
man [e] > coffee-house > initial-state > run
[info] Running com.typesafe.training.coffeehouse.CoffeeHouseApp
[WARN] [05/27/2015 12:56:08.967] [run-main-0] [...coffeehouse.CoffeeHouseApp(akka://coffee-house-system)] CoffeeHouseApp running
Enter commands into the terminal, e.g. q or quit
```

- Use the `test` command to verify the initial state works as expected. You should see something like the following:

```scala
...
[info] Test run started
[info] Test com.typesafe.training.coffeehouse.CoffeeTest.otherDrinkShouldReturnDrinkDifferentFromGivenCode started
[info] Test com.typesafe.training.coffeehouse.CoffeeTest.orderShouldCreateCorrectBeverageForGivenCode started
[info] Test com.typesafe.training.coffeehouse.CoffeeTest.orderShouldThrowExceptionForWrongBeverageCode started
[info] Test com.typesafe.training.coffeehouse.CoffeeTest.coffeesShouldContains_AkkaccinoCaffeJavaAndMochaPlay started
[info] Test run finished: 0 failed, 0 ignored, 4 total, 0.146s
[info] Test run started
[info] Test com.typesafe.training.coffeehouse.TerminalTest.shouldCreateUnknownFromCommand started
[info] Test com.typesafe.training.coffeehouse.TerminalTest.shouldCreateCorrectGuestFromCommand started
[info] Test com.typesafe.training.coffeehouse.TerminalTest.shouldCreateGetStatusFromCommand started
[info] Test com.typesafe.training.coffeehouse.TerminalTest.shouldCreateQuitFromCommand started
[info] Test run finished: 0 failed, 0 ignored, 4 total, 0.005s
[info] Test run started
[info] Test com.typesafe.training.coffeehouse.CoffeeHouseAppTest.argsToOptsShouldConvertArgsToOpts started
[info] Test com.typesafe.training.coffeehouse.CoffeeHouseAppTest.applySystemPropertiesShouldConvertOptsToSystemProps started
[info] Test run finished: 0 failed, 0 ignored, 2 total, 0.013s
[info] Passed: Total 10, Failed 0, Errors 0, Passed 10
[success] Total time: 1 s, ...
```

- Use the `koan next` command to move to the next exercise.
