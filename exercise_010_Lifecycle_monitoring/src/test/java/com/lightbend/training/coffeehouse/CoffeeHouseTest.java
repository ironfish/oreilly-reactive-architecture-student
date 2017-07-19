package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import org.junit.Test;

public class CoffeeHouseTest extends BaseAkkaTestCase {

    @Test
    public void shouldLogMessageWhenCreated() {
        new JavaTestKit(system) {{
            interceptDebugLogMessage(this, ".*[Oo]pen.*", 1, () -> system.actorOf(CoffeeHouse.props(Integer.MAX_VALUE)));
        }};
    }

    @Test
    public void shouldCreateChildActorCalledBaristaWhenCreated() {
        new JavaTestKit(system) {{
            system.actorOf(CoffeeHouse.props(Integer.MAX_VALUE), "create-barista");
            expectActor(this, "/user/create-barista/waiter");
        }};
    }

    @Test
    public void shouldCreateChildActorCalledWaiterWhenCreated() {
        new JavaTestKit(system) {{
            system.actorOf(CoffeeHouse.props(Integer.MAX_VALUE), "create-waiter");
            expectActor(this, "/user/create-waiter/waiter");
        }};
    }

    @Test
    public void shouldCreateGuestActorsWhenCreateGuestMessageSent() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = system.actorOf(CoffeeHouse.props(Integer.MAX_VALUE), "create-guest");
            coffeeHouse.tell(new CoffeeHouse.CreateGuest(new Coffee.Akkaccino()), ActorRef.noSender());
            expectActor(this, "/user/create-guest/$*");
        }};
    }

    @Test
    public void sendingApproveCoffeeShouldForwardPrepareCoffeeIfCaffeineLimitNotReached() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = createActor(CoffeeHouse.class, "prepare-coffee", () -> new CoffeeHouse(Integer.MAX_VALUE) {
                @Override
                protected ActorRef createBarista() {
                    return getRef();
                }
            });
            coffeeHouse.tell(new CoffeeHouse.CreateGuest(new Coffee.Akkaccino()), ActorRef.noSender());
            ActorRef guest = expectActor(this, "/user/prepare-coffee/$*");
            coffeeHouse.tell(new CoffeeHouse.ApproveCoffee(new Coffee.Akkaccino(), guest), getRef());
            expectMsgEquals(new Barista.PrepareCoffee(new Coffee.Akkaccino(), guest));
        }};
    }

    @Test
    public void sendingApproveCoffeeShouldResultInLoggingStatusMessageWhenLimitReached() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = system.actorOf(CoffeeHouse.props(1), "caffeine-limit");
            coffeeHouse.tell(new CoffeeHouse.CreateGuest(new Coffee.Akkaccino()), ActorRef.noSender());
            ActorRef guest = expectActor(this, "/user/caffeine-limit/$*");
            interceptInfoLogMessage(this, ".*[Ss]orry.*", 1, () -> coffeeHouse.tell(
                    new CoffeeHouse.ApproveCoffee(new Coffee.Akkaccino(), guest), ActorRef.noSender()));
        }};
    }

    @Test
    public void sendingApproveCoffeeShouldResultInStoppingGuestWhenLimitReached() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = system.actorOf(CoffeeHouse.props(1), "guest-terminated");
            coffeeHouse.tell(new CoffeeHouse.CreateGuest(new Coffee.Akkaccino()), ActorRef.noSender());
            ActorRef guest = expectActor(this, "/user/guest-terminated/$*");
            watch(guest);
            coffeeHouse.tell(new CoffeeHouse.ApproveCoffee(new Coffee.Akkaccino(), guest), ActorRef.noSender());
            expectTerminated(guest);
        }};
    }

    @Test
    public void onTerminationOfGuestCoffeeHouseShouldRemoveGuestFromBookkeeper() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = system.actorOf(CoffeeHouse.props(1), "guest-removed");
            coffeeHouse.tell(new CoffeeHouse.CreateGuest(new Coffee.Akkaccino()), ActorRef.noSender());
            ActorRef guest = expectActor(this, "/user/guest-removed/$*");
            interceptDebugLogMessage(this, ".*[Rr]emoved.*", 1, () -> {
                coffeeHouse.tell(new CoffeeHouse.ApproveCoffee(new Coffee.Akkaccino(), guest), ActorRef.noSender());
            });
        }};
    }
}


