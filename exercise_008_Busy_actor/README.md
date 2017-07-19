busy-actor

# Exercise 8 > Busy Actor

In this exercise, we will introduce a `Barista` actor who specializes in making our fine caffeinated beverages and will keep our other actors busy.

- Create the `Barista` actor in the `com.lightbend.training.coffeehouse` package as follows:
    - Add a `prepareCoffeeDuration` parameter of type `FiniteDuration`.
    - Define a `Props` factory.
    - Create a `PrepareCoffee` message with parameters of `coffee` type `Coffee` and `guest` type `ActorRef`.
    - Create a `CoffeePrepared` message with parameters of `coffee` type `Coffee` and `guest` type `ActorRef`.
    - Define the behavior as when `PrepareCoffee(coffee, guest)` is received:
        - Busily prepare coffee for `prepareCoffeeDuration`.
        - Respond with `CoffeePrepared(coffee, guest)` to the sender.
        - **HINT**: Use `Thread.sleep` to simulate being busy.
        - **NOTE**: Never use `Thread.sleep` in production as it blocks.        
- Change `Waiter` as follows:
    - Add a reference to the `Barista` actor.
    - Instead of serving coffee immediately, defer to the `Barista` for preparation.
- Change `CoffeeHouse` as follows:
    - Create a `private barista` actor with name `barista`.
    - Use a `createBarista` factory method.
    - For `prepareCoffeeDuration`, use a configuration value with key `coffee-house.barista.prepare-coffee-duration`.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Attention:
        - Enter `g {coffee}` or `guest {coffee}` where {coffee} must be one of the defined coffees (`a`, `m`, or `c`).
        - If you omit {coffee}, `Akkaccino` will be used by default.
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
    - Lifecycle debug messages are logged to `coffee-house.log`.
    - Make sure the correct number of `Guest` creations were logged.
    - Make sure the guests are enjoying their `yummy` coffee.
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.