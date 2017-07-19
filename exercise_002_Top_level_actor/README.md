top-level-actor

# Exercise 2 > Top Level Actor

In this exercise, we will make `CoffeeHouse` a top-level actor and implement some configuration properties.

- In the `CoffeeHouse` actor:
    - Implement a `Props` factory method.
    - Log "CoffeeHouse Open" at `debug` when the `CoffeeHouse` actor is created.
- In `CoffeeHouseApp` create a top-level actor named `coffee-house`.
- Create a configuration file that does the following:
    - Set the Akka logging level to `debug`.
    - Turn on logging of `unhandled` messages.
    - Use the `Slf4jLogger`.
- Use the `run` command to boot the `CoffeeHouseApp` and verify: 
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.