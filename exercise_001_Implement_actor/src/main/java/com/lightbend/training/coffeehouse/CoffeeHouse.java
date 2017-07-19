/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;

public class CoffeeHouse extends AbstractLoggingActor {

    public CoffeeHouse() {
    }

    // @todo implement createReceive and use receiveBuilder
    @Override
    public Receive createReceive() {
        return receiveBuilder().
                matchAny(o -> log().info("Coffee Brewing")).build();
    }
}
