package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import org.junit.Test;

public class WaiterTest extends BaseAkkaTestCase {

    @Test
    public void sendingServeCoffeeShouldResultInCoffeeServedResponse() {
        new JavaTestKit(system) {{
            ActorRef waiter = system.actorOf(Waiter.props());
            waiter.tell(new Waiter.ServeCoffee(new Coffee.Akkaccino()), getRef());
            expectMsgEquals(new Waiter.CoffeeServed(new Coffee.Akkaccino()));
        }};
    }
}
