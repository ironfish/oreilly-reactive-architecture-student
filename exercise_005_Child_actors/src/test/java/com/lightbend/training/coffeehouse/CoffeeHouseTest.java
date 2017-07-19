package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.junit.Test;

public class CoffeeHouseTest extends BaseAkkaTestCase {

    @Test
    public void shouldLogMessageWhenCreated() {
        new JavaTestKit(system) {{
            interceptDebugLogMessage(this, ".*[Oo]pen.*", 1, () -> system.actorOf(CoffeeHouse.props()));
        }};
    }

    @Test
    public void shouldCreateGuestActorsWhenCreateGuestMessageSent() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = system.actorOf(Props.create(CoffeeHouse.class), "create-guest");
            coffeeHouse.tell(CoffeeHouse.CreateGuest.Instance, ActorRef.noSender());
            expectActor(this, "/user/create-guest/$*");
        }};
    }
}


