/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class CoffeeHouse extends AbstractLoggingActor {

    private final FiniteDuration baristaPrepareCoffeeDuration =
            Duration.create(
                    context().system().settings().config().getDuration(
                            "coffee-house.barista.prepare-coffee-duration", MILLISECONDS), MILLISECONDS);

    private final FiniteDuration guestFinishCoffeeDuration =
            Duration.create(
                    context().system().settings().config().getDuration(
                            "coffee-house.guest.finish-coffee-duration", MILLISECONDS), MILLISECONDS);

    private final ActorRef barista =
            createBarista();

    private final ActorRef waiter =
            createWaiter();

    private final Map<ActorRef, Integer> guestCaffeineBookkeeper = new ConcurrentHashMap<>();

    private final int caffeineLimit;

    public CoffeeHouse(int caffeineLimit) {
        log().debug("CoffeeHouse Open");
        this.caffeineLimit = caffeineLimit;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(CreateGuest.class, createGuest -> {
                    final ActorRef guest = createGuest(createGuest.favoriteCoffee, createGuest.caffeineLimit);
                    addGuestToBookkeeper(guest);
                    context().watch(guest);
                }).
                match(ApproveCoffee.class, this::coffeeApproved, approveCoffee ->
                        barista.forward(new Barista.PrepareCoffee(approveCoffee.coffee, approveCoffee.guest), context())
                ).
                match(ApproveCoffee.class, approveCoffee -> {
                    log().info("Sorry, {}, but you have reached your limit.", approveCoffee.guest.path().name());
                    context().stop(approveCoffee.guest);
                }).
                match(Terminated.class, terminated -> {
                    log().info("Thanks, {}, for being our guest!", terminated.getActor());
                    removeGuestFromBookkeeper(terminated.getActor());
                }).build();
    }

    public static Props props(int caffeineLimit) {
        return Props.create(CoffeeHouse.class, () -> new CoffeeHouse(caffeineLimit));
    }

    private boolean coffeeApproved(ApproveCoffee approveCoffee) {
        final int guestCaffeineCount = guestCaffeineBookkeeper.get(approveCoffee.guest);
        if (guestCaffeineCount < caffeineLimit) {
            guestCaffeineBookkeeper.put(approveCoffee.guest, guestCaffeineCount + 1);
            return true;
        }
        return false;
    }

    private void addGuestToBookkeeper(ActorRef guest) {
        guestCaffeineBookkeeper.put(guest, 0);
        log().debug("Guest {} added to bookkeeper", guest);
    }

    private void removeGuestFromBookkeeper(ActorRef guest) {
        guestCaffeineBookkeeper.remove(guest);
        log().debug("Removed guest {} from bookkeeper", guest);
    }

    protected ActorRef createBarista() {
        return context().actorOf(Barista.props(baristaPrepareCoffeeDuration), "barista");
    }

    protected ActorRef createWaiter() {
        return context().actorOf(Waiter.props(self()), "waiter");
    }

    // todo So that a `Guest` can be created with a `caffeineLimit`.
    protected ActorRef createGuest(Coffee favoriteCoffee, int caffeineLimit) {
        return getContext().actorOf(Guest.props(waiter, favoriteCoffee, guestFinishCoffeeDuration, caffeineLimit));
    }

    public static final class CreateGuest {

        public final Coffee favoriteCoffee;

        public final int caffeineLimit;

        public CreateGuest(final Coffee favoriteCoffee, final int caffeineLimit) {
            checkNotNull(favoriteCoffee, "Favorite coffee cannot be null");
            this.favoriteCoffee = favoriteCoffee;
            this.caffeineLimit = caffeineLimit;
        }

        @Override
        public String toString() {
            return "CreateGuest{"
                    + "favoriteCoffee=" + favoriteCoffee + ", "
                    + "caffeineLimit=" + caffeineLimit + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof CreateGuest) {
                CreateGuest that = (CreateGuest) o;
                return (this.favoriteCoffee.equals(that.favoriteCoffee))
                        && (this.caffeineLimit == that.caffeineLimit);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = 1;
            h *= 1000003;
            h ^= favoriteCoffee.hashCode();
            h *= 1000003;
            h ^= caffeineLimit;
            return h;
        }
    }

    public static final class ApproveCoffee {

        public final Coffee coffee;

        public final ActorRef guest;

        public ApproveCoffee(final Coffee coffee, final ActorRef guest) {
            checkNotNull(coffee, "Coffee cannot be null");
            checkNotNull(guest, "Guest cannot be null");
            this.coffee = coffee;
            this.guest = guest;
        }

        @Override
        public String toString() {
            return "ApproveCoffee{"
                    + "coffee=" + coffee + ", "
                    + "guest=" + guest + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof ApproveCoffee) {
                ApproveCoffee that = (ApproveCoffee) o;
                return (this.coffee.equals(that.coffee))
                        && (this.guest.equals(that.guest));
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = 1;
            h *= 1000003;
            h ^= coffee.hashCode();
            h *= 1000003;
            h ^= guest.hashCode();
            return h;
        }
    }
}
