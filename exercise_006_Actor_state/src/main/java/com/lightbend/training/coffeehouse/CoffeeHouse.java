/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import static com.google.common.base.Preconditions.checkNotNull;

public class CoffeeHouse extends AbstractLoggingActor {

    // todo Create a `private waiter` actor with name `waiter`.
    private final ActorRef waiter = createWaiter();

    public CoffeeHouse() {
        log().debug("CoffeeHouse Open");
   }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(
                CreateGuest.class, createGuest -> createGuest(createGuest.favoriteCoffee)).build();
    }

    public static Props props() {
        return Props.create(CoffeeHouse.class, CoffeeHouse::new);
    }

    // todo Use a `createWaiter` factory method.
    protected ActorRef createWaiter() {
        return getContext().actorOf(Waiter.props(), "waiter");
    }
    // todo Add `favoriteCoffee` parameter of type `Coffee` to the `CreateGuest` message.
    protected void createGuest(Coffee favoriteCoffee) {
        getContext().actorOf(Guest.props(waiter, favoriteCoffee));
    }

    // todo Update the `createGuest` factory method to account for the `waiter` and `favoriteCoffee` parameters.
    public static final class CreateGuest {

        public final Coffee favoriteCoffee;

        public CreateGuest(final Coffee favoriteCoffee) {
            checkNotNull(favoriteCoffee, "Favorite coffee cannot be null");
            this.favoriteCoffee = favoriteCoffee;
        }

        @Override
        public String toString() {
            return "CreateGuest{favoriteCoffee=" + favoriteCoffee + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof CreateGuest) {
                CreateGuest that = (CreateGuest) o;
                return this.favoriteCoffee.equals(that.favoriteCoffee);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = 1;
            h *= 1000003;
            h ^= favoriteCoffee.hashCode();
            return h;
        }
    }
}
