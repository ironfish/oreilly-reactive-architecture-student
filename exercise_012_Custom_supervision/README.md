custom-supervision

# Exercise 12 > Custom Supervision

In this exercise, we will further explore resilience by implementing custom supervision.

- Look up the default supervisor strategy in the `Akka` documentation.
- Change `CoffeeHouse` as follows:
    - Caffeinated `Guest` actors should not restart.
    - Apply a custom supervision strategy that stops them instead.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Create a `Guest` with an individual `caffeineLimit` less than the global one and watch its lifecycle.
    - Make sure the `Guest` actor throws `CaffeineException`.
    - Verify that the caffeinated `Guest` does not restart.
    - Attention: 
        - Enter g (a/c/m) 2 or guest (a/c/m) 2 to create a `Guest` with a `caffeineLimit` of 2.
        - If you omit the limit, `Integer.MAX_VALUE` will be used by default.
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.