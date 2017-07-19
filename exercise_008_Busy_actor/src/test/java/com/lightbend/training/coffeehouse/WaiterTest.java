package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import akka.testkit.TestProbe;
import org.junit.Test;

public class WaiterTest extends BaseAkkaTestCase {

    @Test
    public void sendingServeCoffeeShouldResultInPrepareCoffeeToBarista() {
        new JavaTestKit(system) {{
            ActorRef barista = getRef();
            TestProbe guest = new TestProbe(system);
            ActorRef waiter = system.actorOf(Waiter.props(barista));
            waiter.tell(new Waiter.ServeCoffee(new Coffee.Akkaccino()), guest.ref());
            expectMsgEquals(new Barista.PrepareCoffee(new Coffee.Akkaccino(), guest.ref()));
        }};
    }
}
