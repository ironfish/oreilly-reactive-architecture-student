message-actor

# Exercise 3 > Message Actor

In this exercise, we will send a message to our `CoffeeHouse`.

- In `CoffeeHouseApp` send `CoffeeHouse` the "Brew Coffee" message.
- Use `ActorRef.noSender()` as sender of the message.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
    - `Coffee Brewing` is logged to `coffee-house.log`.
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.