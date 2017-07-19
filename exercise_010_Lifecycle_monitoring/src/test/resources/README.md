lifecycle-monitoring

# Exercise 10 > Lifecycle Monitoring

Sometimes we need to perform certain tasks before stopping an actor. In this exercise, we will explore this idea by watching for the `Termination` message.

- Change `CoffeeHouse` to monitor each `Guest` as follows:
    - When the `Guest` terminates, remove the `Guest` from caffeineLimit bookkeeping.
    - Log "Thanks {guest}, for being our guest!" at `info`.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
    - Lifecycle debug messages are logged to `coffee-house.log`.
    - Make sure the correct number of `Guest` creations were logged.
    - Make sure the correct number of `Guest` actors were added to the guest book.
    - Make sure the `Guest` actors caffeine count is incremented.
    - Make sure the guests are enjoying their `yummy` coffee.
    - Make sure your `Guest` actors are stopped as expected.
    - Make sure your `Guest` actors were removed from the guest book.
- Use the `test` command to verify the solution works as expected.
- Use the `koan next` command to move to the next exercise.