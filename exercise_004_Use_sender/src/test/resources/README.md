use-sender

# Exercise 4 > Use Sender

In this exercise, we will use the sender to respond from `CoffeeHouse`.

- Change `CoffeeHouse` as follows:
    - Instead of logging "Coffee Brewing", respond to the `sender()`.
- In `CoffeeHouseApp` create an anonymous actor that does the following:
    - In the constructor send `CoffeeHouse` a "Brew Coffee" message.
    - Pass `self()` as a sender
    - Log the message received at `info`.
    - **HINT**: Creating anonymous actor:

```scala
Props.create(AbstractLoggingActor.class, () -> new AbstractLoggingActor(){
            {
            	coffeeHouse.tell(...)
                receive(ReceiveBuilder.matchAny(...)).build());
            }
        });    
```

- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - `CoffeeHouse Open` is logged to `coffee-house.log`.
    - `Coffee Brewing` is logged to `coffee-house.log`.
- Use the `test` command to verify the solution works as expected.
- Use the `koan next` command to move to the next exercise.
