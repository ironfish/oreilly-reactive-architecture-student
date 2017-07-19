/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class CoffeeHouse extends AbstractLoggingActor {

    // todo For `prepareCoffeeDuration`, use a configuration value with key `coffee-house.barista.prepare-coffee-duration`.
    private final FiniteDuration baristaPrepareCoffeeDuration =
            Duration.create(
                    context().system().settings().config().getDuration(
                            "coffee-house.barista.prepare-coffee-duration", MILLISECONDS), MILLISECONDS);

    private final FiniteDuration guestFinishCoffeeDuration =
            Duration.create(
                    context().system().settings().config().getDuration(
                            "coffee-house.guest.finish-coffee-duration", MILLISECONDS), MILLISECONDS);

    // todo Create a `private barista` actor with name `barista`.
    private final ActorRef barista =
            createBarista();

    private final ActorRef waiter =
            createWaiter();

    public CoffeeHouse() {
        log().debug("CoffeeHouse Open");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(CreateGuest.class, createGuest ->
                        createGuest(createGuest.favoriteCoffee)
                ).build();
    }

    public static Props props() {
        return Props.create(CoffeeHouse.class, CoffeeHouse::new);
    }

    // todo Use a `createBarista` factory method.
    protected ActorRef createBarista() {
        return context().actorOf(Barista.props(baristaPrepareCoffeeDuration), "barista");
    }

    protected ActorRef createWaiter() {
        return context().actorOf(Waiter.props(barista), "waiter");
    }

    protected void createGuest(Coffee favoriteCoffee) {
        context().actorOf(Guest.props(waiter, favoriteCoffee, guestFinishCoffeeDuration));
    }

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
