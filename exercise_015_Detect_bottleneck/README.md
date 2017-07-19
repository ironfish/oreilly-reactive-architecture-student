detect-bottleneck

# Exercise 15 > Detect Bottleneck

In this exercise, we will change our configuration settings to see if we can detect a bottleneck.

1. Make the following changes to the configuration files:
    - Set `caffeine-limit` to 1000 (no more caffeinated guests).
    - Set `prepare-coffee-duration` to 2 seconds.
    - Set `accuracy` to 100 (no more frustrated waiters).
    - Set `finish-coffee-duration` to 2 seconds.
- Use the `run` command to boot the `CoffeeHouseApp` and verify:
    - Create one `Guest`, then another, then some more and watch the throughput per `Guest`.
    - QUIZ: Why does the application not scale?
    - QUIZ: Where is the bottleneck?
- Use the `test` command to verify the solution works as expected.
- Use the `next` command to move to the next exercise.