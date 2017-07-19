package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import akka.testkit.TestProbe;
import org.junit.Test;

public class WaiterTest extends BaseAkkaTestCase {

    @Test
    public void sendingServeCoffeeShouldResultInApproveCoffeeToCoffeeHouse() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = getRef();
            TestProbe guest = new TestProbe(system);
            ActorRef waiter = system.actorOf(Waiter.props(coffeeHouse));
            waiter.tell(new Waiter.ServeCoffee(new Coffee.Akkaccino()), guest.ref());
            expectMsgEquals(new CoffeeHouse.ApproveCoffee(new Coffee.Akkaccino(), guest.ref()));
        }};
    }
}
