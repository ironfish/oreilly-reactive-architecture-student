faulty-waiter

# Exercise 13 > Faulty Waiter

In this exercise, we will introduce another faulty actor in the form of our `Barista` where sometimes they make the wrong coffee. When this happens, the `Guest` will complain and reorder. If the `Waiter` receives too many complaints, he will become frustrated.

- Change the `Barista` as follows:
    - Add an `accuracy` parameter of type `Int` expressing a percentage.
    - Get a random `Int` value less than 100.
    - If the random `Int` is less than `accuracy`:
        - Prepare the correct `Coffee`.
        - Otherwise prepare a wrong one.
        - *HINT*: See `Coffee.orderOther` method.
- Change the `Waiter` as follows:
    - Add a `Complaint` message with parameter `coffee` of type `Coffee`.
    - Add a `FrustratedException` type extending `IllegalStateException`.
    - Add a `barista` parameter of type `ActorRef` and a `maxComplaintCount` parameter of type `Int`.
    - Keep track of the number of `Complaint` messages received.
    - If more `Complaint` messages arrive than the `maxComplaintCount`:
        - Throw a `FrustratedException`
        - Else send `PrepareCoffee` to the `Barista`.
- Change the `Guest` as follows:
    - On receiving the wrong `Coffee`, send a `Complaint` to the `Waiter`.
    - Log at `info` when receiving the wrong `Coffee` (ie. `Expected a {}, but got a {}!`)
    - Which argument needs to be given for `coffee`?
- Change `CoffeeHouse` as follows:
    - For `accuracy` use a configuration value with key `coffee-house.barista.accuracy`.
    - For `maxComplaintCount` use a configuration value with key `coffee-house.waiter.max-complaint-count`.
    - Don't forget to use the new parameters when creating the `Barista` and `Waiter`.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Create a `Guest` and see what happens when the `Waiter` gets frustrated.
    - Attention:
        - You might need to use small `accuracy` and `maxComplainCount` values.
- Use the `test` command to verify the solution works as expected.
- Use the `koan next` command to move to the next exercise.
