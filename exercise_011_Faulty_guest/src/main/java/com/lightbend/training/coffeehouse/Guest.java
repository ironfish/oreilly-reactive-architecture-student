/*
 * Copyright © 2014 Typesafe, Inc. All rights reserved.
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.duration.FiniteDuration;

public class Guest extends AbstractLoggingActor {

    private final ActorRef waiter;

    private final Coffee favoriteCoffee;

    private final FiniteDuration finishCoffeeDuration;

    // todo Add a `caffeineLimit` parameter.
    private final int caffeineLimit;

    private int coffeeCount = 0;

    public Guest(ActorRef waiter, Coffee favoriteCoffee, FiniteDuration finishCoffeeDuration, int caffeineLimit) {
        this.waiter = waiter;
        this.favoriteCoffee = favoriteCoffee;
        this.finishCoffeeDuration = finishCoffeeDuration;
        this.caffeineLimit = caffeineLimit;
        orderFavoriteCoffee();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(Waiter.CoffeeServed.class, coffeeServed -> {
                    coffeeCount++;
                    log().info("Enjoying my {} yummy {}!", coffeeCount, coffeeServed.coffee);
                    scheduleCoffeeFinished();
                }).
                // todo Upon receiving `CoffeeFinished` throw the `CaffeineException` if `coffeeCount` exceeds `caffeineLimit`.
                match(CoffeeFinished.class, coffeeFinished -> coffeeCount > this.caffeineLimit, coffeeFinished -> {
                    throw new CaffeineException();
                }).
                match(CoffeeFinished.class, coffeeFinished ->
                        orderFavoriteCoffee()
                ).build();
    }

    public static Props props(final ActorRef waiter, final Coffee favoriteCoffee,
                              final FiniteDuration finishCoffeeDuration, final int caffeineLimit) {
        return Props.create(Guest.class,
                () -> new Guest(waiter, favoriteCoffee, finishCoffeeDuration, caffeineLimit));
    }

    @Override
    public void postStop() {
        log().info("Goodbye!");
    }

    private void orderFavoriteCoffee() {
        waiter.tell(new Waiter.ServeCoffee(favoriteCoffee), self());
    }

    private void scheduleCoffeeFinished() {
        context().system().scheduler().scheduleOnce(finishCoffeeDuration, self(),
                CoffeeFinished.Instance, context().dispatcher(), self());
    }

    // todo Create new exception called `CaffeineException` by extending `IllegalStateException`.
    public static final class CaffeineException extends IllegalStateException {
        static final long serialVersionUID = 1;

        public CaffeineException() {
            super("Too much caffeine!");
        }
    }

    public static final class CoffeeFinished {

        public static final CoffeeFinished Instance = new CoffeeFinished();

        private CoffeeFinished() {
        }
    }
}
