/*
 * Copyright © 2014 Typesafe, Inc. All rights reserved.
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Guest extends AbstractLoggingActor {

    private final ActorRef waiter;

    private final Coffee favoriteCoffee;

    private int coffeeCount = 0;

    public Guest(ActorRef waiter, Coffee favoriteCoffee) {
        this.waiter = waiter;
        this.favoriteCoffee = favoriteCoffee;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(Waiter.CoffeeServed.class, coffeeServed -> {
                    coffeeCount++;
                    log().info("Enjoying my {} yummy {}!", coffeeCount, coffeeServed.coffee);
                }).
                        match(CoffeeFinished.class, coffeeFinished ->
                        this.waiter.tell(new Waiter.ServeCoffee(this.favoriteCoffee), self())
                ).build();
    }

    public static Props props(final ActorRef waiter, final Coffee favoriteCoffee) {
        return Props.create(Guest.class, () -> new Guest(waiter, favoriteCoffee));
    }

    public static final class CoffeeFinished {

        public static final CoffeeFinished Instance =
                new CoffeeFinished();

        private CoffeeFinished() {
        }
    }
}
