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

    // todo Add a local mutable `coffeeCount` field of type `int`.
    private int coffeeCount = 0;

    // todo Add a `waiter` parameter of type `ActorRef`.
    // todo Add a `favoriteCoffee` parameter of type `Coffee`.
    public Guest(ActorRef waiter, Coffee favoriteCoffee) {
        this.waiter = waiter;
        this.favoriteCoffee = favoriteCoffee;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(Waiter.CoffeeServed.class, coffeeServed -> {
                    // todo Increase the `coffeeCount` by one.
                    // todo Log `Enjoying my {coffeeCount} yummy {coffee}!` at `info`.
                    coffeeCount++;
                    log().info("Enjoying my {} yummy {}!", coffeeCount, coffeeServed.coffee);
                }).
                // todo When `CoffeeFinished` is received, respond with `ServeCoffee(favoriteCoffee)` to `waiter`.
                match(CoffeeFinished.class, coffeeFinished ->
                        this.waiter.tell(new Waiter.ServeCoffee(this.favoriteCoffee), self())
                ).build();
    }

    public static Props props(final ActorRef waiter, final Coffee favoriteCoffee) {
        return Props.create(Guest.class, () -> new Guest(waiter, favoriteCoffee));
    }

    // todo Create a `CoffeeFinished` message. Make it a singleton object.
    public static final class CoffeeFinished {

        public static final CoffeeFinished Instance =
                new CoffeeFinished();

        private CoffeeFinished() {
        }
    }
}
