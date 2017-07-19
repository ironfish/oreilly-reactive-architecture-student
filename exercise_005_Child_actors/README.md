child-actors

# Exercise 5 > Child Actors

In this exercise, we will use `CoffeeHouse` to create `Guest` as a child actor.

- Create the `Guest` actor in the `com.lightbend.training.coffeehouse` package as follows:
    - Create a `Props` factory for `Guest`.
    - Implement the behavior as `empty`.
    - Attention: See `Actor.unhandled`.
- Change `CoffeeHouse` as follows:
    - Add `CreateGuest` message. Make it a singleton object.
    - Use a `createGuest` factory method that creates a child `Guest` actor without `name`.
    - Upon receiving the `CreateGuest` message call the factory method.
- Change `CoffeeHouseApp` as follows:
    - Remove the `anonymous` actor.
    - When handling `TerminalCommand.Guest`, send `CreateGuest` to `CoffeeHouse`.
    - Make sure you account for the creating of 1 or more `Guest` actors.
- Change the `src/main/resource/application.conf` as follows:
    - Turn on `lifecycle` debugging.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Attention: 
        - Enter `5 g` or `5 guest` to create five `Guest` actors.
        - If you omit the count, `1` is used by default.
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
    - Lifecycle debug messages are logged to `coffee-house.log`.
    - Make sure the correct number of `Guest` creations were logged.
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.