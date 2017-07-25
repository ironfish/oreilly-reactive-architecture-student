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
        return receiveBuilder().
                matchAny(o -> log().info("Coffee Brewing")).build();
    }

    public static Props props() {
        return Props.create(CoffeeHouse.class, CoffeeHouse::new);
    }
}
