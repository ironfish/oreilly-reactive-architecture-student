stop-actor

# Exercise 9 > Stop Actor

In this exercise, we will limit the number of coffees a `Guest` consumes by setting a caffeine limit per `Guest`. When the `Guest` reaches their limit, we will stop the actor.

- Change `CoffeeHouse` as follows:
    - Create an `ApproveCoffee` message with parameters of `coffee` type `Coffee` and `guest` type `ActorRef`.
    - Add a `caffeineLimit` parameter of type `int`.
    - When creating the `Waiter` pass along `self()` instead of `Barista`.
    - Add to the behavior, `CreateGuest`:
        - Add `guest` to `guestBook` with a caffeine count of 0.
        - Log `Guest {guest} added to book` at `info`.
    - Add to the behavior, `ApproveCoffee`:
        - Look at the current `Guest` caffeineLimit.
        - If less than `caffeineLimit`:
            - Send `PrepareCoffee` to the `Barista`.
            - Log `Guest {guest} caffeine count incremented.` at `info`.
        - Else:
            - Log `Sorry, {guest}, but you have reached your limit.` at `info`.
            - Stop the `Guest`.
- Change `Waiter` as follows:
    - Rename the `barista` parameter to `coffeeHouse`.
    - Change the behavior to reflect using `CoffeeHouse`.
- Change `Guest` as follows:
    - Override the `postStop` hook to log `Goodbye!` at `info`.
- Change `CoffeeHouseApp` as follows:
    - Get the caffeineLimit from configuration property `coffee-house.caffeine-limit`.
- Run the `run` command to boot the `CoffeeHouseApp` and verify:
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
    - Lifecycle debug messages are logged to `coffee-house.log`.
    - Make sure the correct number of `Guest` creations were logged.
    - Make sure the correct number of `Guest` actors were added to the guest book.
    - Make sure the `Guest` actors caffeine count is incremented.
    - Make sure the guests are enjoying their `yummy` coffee.
    - Make sure your `Guest` actors are stopped as expected.
- QUIZ: Your implementation may have a hidden issue; see if you can find it!
- Use the `test` command to verify the solution works as expected.
- Use the `koan next` command to move to the next exercise.
