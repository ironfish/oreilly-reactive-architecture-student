package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.junit.Test;

public class CoffeeHouseTest extends BaseAkkaTestCase {

    @Test
    public void onReceiveOfMessageCoffeeHouseShouldLogMessage() {
        new JavaTestKit(system) {{
            ActorRef coffeeHouse = system.actorOf(Props.create(CoffeeHouse.class));
            interceptInfoLogMessage(this, ".*[Cc]offee.*", 1, () -> coffeeHouse.tell("Brew Coffee", ActorRef.noSender()));
        }};
    }

    @Test
    public void shouldLogMessageWhenCreated() {
        new JavaTestKit(system) {{
            interceptDebugLogMessage(this, ".*[Oo]pen.*", 1, () -> system.actorOf(CoffeeHouse.props()));
        }};
    }
}


