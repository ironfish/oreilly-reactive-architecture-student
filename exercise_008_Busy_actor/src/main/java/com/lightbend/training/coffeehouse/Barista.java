/*
 * Copyright © 2014 Typesafe, Inc. All rights reserved.
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.duration.FiniteDuration;

import static com.google.common.base.Preconditions.checkNotNull;

public class Barista extends AbstractLoggingActor {

    // todo Add a `prepareCoffeeDuration` parameter of type `FiniteDuration`.
    private final FiniteDuration prepareCoffeeDuration;

    public Barista(FiniteDuration prepareCoffeeDuration) {
        this.prepareCoffeeDuration = prepareCoffeeDuration;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(PrepareCoffee.class, prepareCoffee -> {
                    // todo Busily prepare coffee for `prepareCoffeeDuration`.
                    Thread.sleep(this.prepareCoffeeDuration.toMillis()); // Attention: Never block a thread in "real" code!
                    // todo Respond with `CoffeePrepared(coffee, guest)` to the sender.
                    sender().tell(new CoffeePrepared(prepareCoffee.coffee, prepareCoffee.guest), self());
                }).build();
    }

    // todo Define a `Props` factory.
    public static Props props(FiniteDuration prepareCoffeeDuration) {
        return Props.create(Barista.class, () -> new Barista(prepareCoffeeDuration));
    }

    // todo Create a `PrepareCoffee` message with parameters of `coffee` type `Coffee` and `guest` type `ActorRef`.
    public static final class PrepareCoffee {

        public final Coffee coffee;

        public final ActorRef guest;

        public PrepareCoffee(final Coffee coffee, final ActorRef guest) {
            checkNotNull(coffee, "Coffee cannot be null");
            checkNotNull(guest, "Guest cannot be null");
            this.coffee = coffee;
            this.guest = guest;
        }

        @Override
        public String toString() {
            return "PrepareCoffee{"
                    + "coffee=" + coffee + ", "
                    + "guest=" + guest + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof PrepareCoffee) {
                PrepareCoffee that = (PrepareCoffee) o;
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

    // todo Create a `CoffeePrepared` message with parameters of `coffee` type `Coffee` and `guest` type `ActorRef`.
    public static final class CoffeePrepared {

        public final Coffee coffee;

        public final ActorRef guest;

        public CoffeePrepared(final Coffee coffee, final ActorRef guest) {
            checkNotNull(coffee, "Coffee cannot be null");
            checkNotNull(guest, "Guest cannot be null");
            this.coffee = coffee;
            this.guest = guest;
        }

        @Override
        public String toString() {
            return "CoffeePrepared{"
                    + "coffee=" + coffee + ", "
                    + "guest=" + guest + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof CoffeePrepared) {
                CoffeePrepared that = (CoffeePrepared) o;
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
