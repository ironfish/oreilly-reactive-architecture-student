use-scheduler

# Exercise 6 > Use Scheduler

In this exercise, we will implement the Akka `Scheduler` to simulate the `Guest` ordering more `Coffee`.

- Change `Guest` as follows:
    - Add a `finishCoffeeDuration` parameter of type `scala.concurrent.duration.FiniteDuration`.
    - Change the behavior on receiving `CoffeeServed` to schedule the sending of `CoffeeFinished` to the `Guest`.
    - QUIZ: What other changes are needed for the guest to enjoy their coffee?
- Change `CoffeeHouse` as follows:
    - Adjust the code for creating a new `Guest`.
    - For `finishCoffeeDuration`, use a configuration value with key `coffee-house.guest.finish-coffee-duration`.
    - To get the configuration value, use the `getDuration` method on `context().system().settings().config()`.
    - **HINT**: Use `scala.concurrent.duration.Duration.create(long, TimeUnit)` to create `FiniteDuration`.
- Change the `src/main/resource/application.conf` as follows:
    - Add a section for `coffee-house` with `guest` that has the `finish-coffee-duration` property. 
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Attention:
        - Enter `g {coffee}` or `guest {coffee}` where {coffee} must be one of the defined coffees (`a`, `m`, or `c`).
        - If you omit {coffee}, `Akkaccino` will be used by default.
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
    - Lifecycle debug messages are logged to `coffee-house.log`.
    - Make sure the correct number of `Guest` creations were logged.
    - Make sure the guests are enjoying their `yummy` coffee.
- Use the `test` command to verify the solution works as expected.
- Use the `koan next` command to move to the next exercise.
