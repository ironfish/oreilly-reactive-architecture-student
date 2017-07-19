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
            ActorRef waiter = system.actorOf(Waiter.props(coffeeHouse, system.deadLetters(), Integer.MAX_VALUE));
            waiter.tell(new Waiter.ServeCoffee(new Coffee.Akkaccino()), guest.ref());
            expectMsgEquals(new CoffeeHouse.ApproveCoffee(new Coffee.Akkaccino(), guest.ref()));
        }};
    }

    @Test
    public void sendingComplaintShouldResultInPrepareCoffeeToBarista() {
        new JavaTestKit(system) {{
            ActorRef barista = getRef();
            TestProbe guest = new TestProbe(system);
            ActorRef waiter = system.actorOf(Waiter.props(system.deadLetters(), barista, 1));

            waiter.tell(new Waiter.Complaint(new Coffee.Akkaccino()), guest.ref());
            expectMsgEquals(new Barista.PrepareCoffee(new Coffee.Akkaccino(), guest.ref()));
        }};
    }

    @Test
    public void shouldThrowFrustratedExceptionWhenMaxComplaintReached() {
        new JavaTestKit(system) {{
            ActorRef waiter = system.actorOf(Waiter.props(system.deadLetters(), system.deadLetters(), 0));
            eventFilter(this, Waiter.FrustratedException.class, "", 1, () -> {
                waiter.tell(new Waiter.Complaint(new Coffee.Akkaccino()), ActorRef.noSender());
            });
        }};
    }
}
