/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */

package com.lightbend.training.coffeehouse;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class CoffeeHouse extends AbstractLoggingActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                matchAny(o -> log().info("Coffee Brewing")).build();
    }
}
