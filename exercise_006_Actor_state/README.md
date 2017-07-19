actor-state

# Exercise 6 > Actor State

In this exercise, we will implement state by tracking a `Guest` actor's favorite `Coffee`.

- Create the `Waiter` actor in the `com.lightbend.training.coffeehouse` package as follows:
    - Define a `Props` factory.
    - Create a `ServeCoffee` message with parameter `coffee` of type `Coffee`.
    - Create a `CoffeeServed` message with parameter `coffee` of type `Coffee`.
    - Define the behavior as when `ServeCoffee(coffee)` is received, respond with `CoffeeServed(coffee)` to the sender.
- Change `Guest` as follows:
    - Create a `CoffeeFinished` message. Make it a singleton object.
    - Add a `waiter` parameter of type `ActorRef`.
    - Add a `favoriteCoffee` parameter of type `Coffee`.
    - Add a local mutable `coffeeCount` field of type `int`.
    - Define the behavior as when `CoffeeServed(coffee)` is received:
        - Increase the `coffeeCount` by one.
        - Log `Enjoying my {coffeeCount} yummy {coffee}!` at `info`.
        - When `CoffeeFinished` is received, respond with `ServeCoffee(favoriteCoffee)` to `waiter`.
- Change `CoffeeHouse` as follows:
    - Create a `private waiter` actor with name `waiter`.
    - Use a `createWaiter` factory method.
    - Add `favoriteCoffee` parameter of type `Coffee` to the `CreateGuest` message.
    - Update the `createGuest` factory method to account for the `waiter` and `favoriteCoffee` parameters.
- Change `CoffeeHouseApp` as follows:
    - Account for the `favoriteCoffee` parameter required by the `CreateGuest` message.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Attention:
        - Enter `g {coffee}` or `guest {coffee}` where {coffee} must be one of the defined coffees (`a`, `m`, or `c`).
        - If you omit {coffee}, `Akkaccino` will be used by default.
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
    - Lifecycle debug messages are logged to `coffee-house.log`.
    - Make sure the correct number of `Guest` creations were logged.
    - QUIZ: Why don't you see any log messages from `Guest` actors enjoying their coffee?
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.