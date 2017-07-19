implement-actor

# Exercise 1 > Implement Actor

In this exercise, we create the `CoffeeHouse` actor with logging.

- Create the `CoffeeHouse` actor in the `com.lightbend.training.coffeehouse` package as follows:
    - Extend with `AbstractLoggingActor` abstract class.
    - Implement the `createReceive()` method.
    - Use `receiveBuilder()` and define the initial behavior to handle any message by logging `Coffee Brewing` at `info` level.
- Use the `run` command to verify the main class `CoffeeHouseApp` boots up as expected.
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.