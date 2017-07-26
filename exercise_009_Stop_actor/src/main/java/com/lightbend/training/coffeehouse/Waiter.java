/*
 * Copyright © 2014 Typesafe, Inc. All rights reserved.
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import static com.google.common.base.Preconditions.checkNotNull;

public class Waiter extends AbstractLoggingActor {

    private ActorRef barista;

    public Waiter(ActorRef barista) {
        this.barista = barista;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(ServeCoffee.class, serveCoffee ->
                        this.barista.tell(new Barista.PrepareCoffee(serveCoffee.coffee, sender()), self())
                ).
                match(Barista.CoffeePrepared.class, coffeePrepared ->
                        coffeePrepared.guest.tell(new CoffeeServed(coffeePrepared.coffee), self())
                ).build();
    }

    public static Props props(ActorRef barista) {
        return Props.create(Waiter.class, () -> new Waiter(barista));
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
}
