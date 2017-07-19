/*
 * Copyright © 2014 Typesafe, Inc. All rights reserved.
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import static com.google.common.base.Preconditions.checkNotNull;

public class Waiter extends AbstractLoggingActor {

    private ActorRef coffeeHouse;

    private ActorRef barista;

    private int maxComplaintCount;

    private int complaintCount;

    public Waiter(ActorRef coffeeHouse, ActorRef barista, int maxComplaintCount) {
        this.coffeeHouse = coffeeHouse;
        this.barista = barista;
        this.maxComplaintCount = maxComplaintCount;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(ServeCoffee.class, serveCoffee ->
                        this.coffeeHouse.tell(new CoffeeHouse.ApproveCoffee(serveCoffee.coffee, sender()), self())
                ).
                match(Barista.CoffeePrepared.class, coffeePrepared ->
                        coffeePrepared.guest.tell(new CoffeeServed(coffeePrepared.coffee), self())
                ).
                // todo If more `Complaint` messages arrive than the `maxComplaintCount`:
                match(Complaint.class, complaint -> complaintCount == this.maxComplaintCount, complaint -> {
                    // todo Throw a `FrustratedException`
                    throw new FrustratedException();
                }).
                // todo Keep track of the number of `Complaint` messages received.
                match(Complaint.class, complaint -> {
                    complaintCount++;
                    // todo Else send `PrepareCoffee` to the `Barista`.
                    this.barista.tell(new Barista.PrepareCoffee(complaint.coffee, sender()), self());
                }).build();
    }

    // todo Add a `barista` parameter of type `ActorRef` and a `maxComplaintCount` parameter of type `Int`.
    public static Props props(ActorRef coffeeHouse, ActorRef barista, int maxComplaintCount) {
        return Props.create(Waiter.class, () -> new Waiter(coffeeHouse, barista, maxComplaintCount));
    }

    // todo Add a `FrustratedException` type extending `IllegalStateException`.
    public static final class FrustratedException extends IllegalStateException {
        static final long serialVersionUID = 1;

        public FrustratedException() {
            super("Too many complaints!");
        }
    }

    public static final class ServeCoffee {

        public final Coffee coffee;

        public ServeCoffee(final Coffee coffee) {
            checkNotNull(coffee, "Coffee cannot be null");
            this.coffee = coffee;
        }

        @Override
        public String toString() {
            return "ServeCoffee{coffee=" + coffee + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof ServeCoffee) {
                ServeCoffee that = (ServeCoffee) o;
                return this.coffee.equals(that.coffee);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = 1;
            h *= 1000003;
            h ^= coffee.hashCode();
            return h;
        }
    }

    public static final class CoffeeServed {

        public final Coffee coffee;

        public CoffeeServed(final Coffee coffee) {
            checkNotNull(coffee, "Coffee cannot be null");
            this.coffee = coffee;
        }

        @Override
        public String toString() {
            return "CoffeeServed{coffee=" + coffee + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof CoffeeServed) {
                CoffeeServed that = (CoffeeServed) o;
                return this.coffee.equals(that.coffee);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = 1;
            h *= 1000003;
            h ^= coffee.hashCode();
            return h;
        }
    }

    // todo Add a `Complaint` message with parameter `coffee` of type `Coffee`.
    public static final class Complaint {

        public final Coffee coffee;

        public Complaint(final Coffee coffee) {
            checkNotNull(coffee, "Coffee cannot be null");
            this.coffee = coffee;
        }

        @Override
        public String toString() {
            return "Complaint{coffee=" + coffee + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof Complaint) {
                Complaint that = (Complaint) o;
                return this.coffee.equals(that.coffee);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = 1;
            h *= 1000003;
            h ^= coffee.hashCode();
            return h;
        }
    }
}
