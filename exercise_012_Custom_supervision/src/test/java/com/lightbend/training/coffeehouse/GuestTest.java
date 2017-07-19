package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import org.junit.Test;

public class GuestTest extends BaseAkkaTestCase {

    @Test
    public void sendingCoffeeServedShouldIncreaseCoffeeCount() {
        new JavaTestKit(system) {{
            ActorRef guest = system.actorOf(Guest.props(system.deadLetters(), new Coffee.Akkaccino(), duration("100 milliseconds"), Integer.MAX_VALUE));
            interceptInfoLogMessage(this, ".*[Ee]njoy.*1\\.*", 1, () -> {
                guest.tell(new Waiter.CoffeeServed(new Coffee.Akkaccino()), ActorRef.noSender());
            });
        }};
    }

    @Test
    public void serveCoffeeShouldBeSentAfterFinishCoffeeDuration() {
        new JavaTestKit(system) {{
            ActorRef guest = createGuest(this, getRef());
            new Within(duration("50 milliseconds"), duration("200 milliseconds")) {
                @Override
                protected void run() {
                    guest.tell(new Waiter.CoffeeServed(new Coffee.Akkaccino()), ActorRef.noSender());
                    expectMsgEquals(new Waiter.ServeCoffee(new Coffee.Akkaccino()));
                }
            };
        }};
    }

    @Test
    public void sendingCoffeeFinishedShouldResultInServeCoffeeMessageToWaiter() {
        new JavaTestKit(system) {{
            ActorRef guest = createGuest(this, getRef());
            guest.tell(Guest.CoffeeFinished.Instance, ActorRef.noSender());
            expectMsgEquals(new Waiter.ServeCoffee(new Coffee.Akkaccino()));
        }};
    }

    @Test
    public void sendingCoffeeFinishedShouldInExceptionIfCaffeineLimitExceeded() {
        new JavaTestKit(system) {{
            ActorRef guest = system.actorOf(Guest.props(system.deadLetters(), new Coffee.Akkaccino(), duration("100 millis"), -1));
            eventFilter(this, Guest.CaffeineException.class, "", 1, () -> {
                guest.tell(Guest.CoffeeFinished.Instance, ActorRef.noSender());
            });
        }};
    }

    private ActorRef createGuest(JavaTestKit kit, ActorRef waiter) {
        ActorRef guest = system.actorOf(Guest.props(waiter, new Coffee.Akkaccino(), kit.duration("100 milliseconds"), Integer.MAX_VALUE));
        kit.expectMsgEquals(new Waiter.ServeCoffee(new Coffee.Akkaccino())); // Creating Guest immediately sends Waiter.ServeCoffee
        return guest;
    }


}
