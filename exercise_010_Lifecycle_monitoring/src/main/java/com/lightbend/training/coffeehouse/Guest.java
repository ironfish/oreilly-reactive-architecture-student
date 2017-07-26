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

    private int coffeeCount = 0;

    public Guest(ActorRef waiter, Coffee favoriteCoffee, FiniteDuration finishCoffeeDuration) {
        this.waiter = waiter;
        this.favoriteCoffee = favoriteCoffee;
        this.finishCoffeeDuration = finishCoffeeDuration;orderFavoriteCoffee();

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(Waiter.CoffeeServed.class, coffeeServed -> {
                    coffeeCount++;
                    log().info("Enjoying my {} yummy {}!", coffeeCount, coffeeServed.coffee);
                    scheduleCoffeeFinished();
                }).
                match(CoffeeFinished.class, coffeeFinished ->
                        orderFavoriteCoffee()
                ).build();
    }

    public static Props props(final ActorRef waiter, final Coffee favoriteCoffee, FiniteDuration finishCoffeeDuration) {
        return Props.create(Guest.class, () -> new Guest(waiter, favoriteCoffee, finishCoffeeDuration));
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

    public static final class CoffeeFinished {

        public static final CoffeeFinished Instance =
                new CoffeeFinished();

        private CoffeeFinished() {
        }
    }
}
