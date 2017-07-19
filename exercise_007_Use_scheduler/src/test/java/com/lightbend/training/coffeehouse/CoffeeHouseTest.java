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
    public void shouldCreateChildActorCalledWaiterWhenCreated() {
        new JavaTestKit(system) {{
            system.actorOf(Props.create(CoffeeHouse.class), "create-waiter");
            expectActor(this, "/user/create-waiter/waiter");
        }};
    }

    @Test
    public void shouldCreateGuestActorsWhenCreateGuestMessageSent() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = system.actorOf(Props.create(CoffeeHouse.class), "create-guest");
            coffeeHouse.tell(new CoffeeHouse.CreateGuest(new Coffee.Akkaccino()), ActorRef.noSender());
            expectActor(this, "/user/create-guest/$*");
        }};
    }
}


