/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class CoffeeHouse extends AbstractLoggingActor {

    public CoffeeHouse() {
        log().debug("CoffeeHouse Open");
    }

    @Override
    public Receive createReceive() {
        // @todo Upon receiving the `CreateGuest` message call the factory method.
        return receiveBuilder().match(CreateGuest.class, createGuest -> createGuest()).build();
    }

    public static Props props() {
        return Props.create(CoffeeHouse.class, CoffeeHouse::new);
    }

    // @todo Use a `createGuest` factory method that creates a child `Guest` actor without `name`.
    protected void createGuest() {
        context().actorOf(Guest.props());
    }

    // @todo Add `CreateGuest` message. Make it a singleton object.
    public static final class CreateGuest {

        public static final CreateGuest Instance =
                new CreateGuest();

        private CreateGuest() {
        }
    }
}
