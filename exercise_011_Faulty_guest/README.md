faulty-guest

# Exercise 11 > Faulty Guest

In this exercise, we will explore resilience by managing a faulty actor.

- Change `Guest` as follows:
    - Add a `caffeineLimit` parameter.
    - Create new exception called `CaffeineException` by extending `IllegalStateException`.
    - Upon receiving `CoffeeFinished` throw the `CaffeineException` if `coffeeCount` exceeds `caffeineLimit`.
- Change `CoffeeHouse` as follows:
    - So that a `Guest` can be created with a `caffeineLimit`.
    - Log the `Guest` path name instead of just the `Guest`.
- Change `CoffeeHouseApp` as follows:
    - So that a `Guest` can be created with a `caffeineLimit`.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Create a `Guest` with an individual `caffeineLimit` less than the global one and watch its lifecycle.
    - Make sure the `Guest` actor throws `CaffeineException`.
    - Attention: 
        - Enter g (a/c/m) 2 or guest (a/c/m) 2 to create a `Guest` with a `caffeineLimit` of 2.
        - If you omit the limit, `Integer.MAX_VALUE` will be used by default.
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.