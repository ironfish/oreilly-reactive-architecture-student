package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import org.junit.Test;

public class GuestTest extends BaseAkkaTestCase {

    @Test
    public void sendingCoffeeServedShouldIncreaseCoffeeCount() {
        new JavaTestKit(system) {{
            ActorRef guest = system.actorOf(Guest.props(system.deadLetters(), new Coffee.Akkaccino()));
            interceptInfoLogMessage(this, ".*[Ee]njoy.*1\\.*", 1, () -> {
                guest.tell(new Waiter.CoffeeServed(new Coffee.Akkaccino()), ActorRef.noSender());
            });
        }};
    }

    @Test
    public void sendingCoffeeFinishedShouldResultInServeCoffeeMessageToWaiter() {
        new JavaTestKit(system) {{
            ActorRef guest = system.actorOf(Guest.props(getRef(), new Coffee.Akkaccino()));
            guest.tell(Guest.CoffeeFinished.Instance, ActorRef.noSender());
            expectMsgEquals(new Waiter.ServeCoffee(new Coffee.Akkaccino()));
        }};
    }
}
