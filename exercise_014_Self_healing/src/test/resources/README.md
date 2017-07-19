self-healing

# Exercise 14 > Self Healing

In this exercise, we will correct a problem introduced in the last exercise by implement self-healing.

- QUIZ: Why does the message flow in exercise 013 get interrupted?
- Change the message flow by:
    - Providing the `Waiter` actors supervisor with all the necessary information.
    - Attention: Think supervision strategy!
3. Run the `run` command to boot the `CoffeeHouseApp` and verify that `Guest` actors are served even after the `Waiter` gets frustrated.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Create a `Guest` and make sure the `Waiter` gets frustrated.
    - That `Guest` actors are served even after the `Waiter` gets frustrated.
    - Attention:
        - You might need to use small `accuracy` and `maxComplainCount` values.
- Use the `test` command to verify the solution works as expected.
- Use the `koan next` command to move to the next exercise.
