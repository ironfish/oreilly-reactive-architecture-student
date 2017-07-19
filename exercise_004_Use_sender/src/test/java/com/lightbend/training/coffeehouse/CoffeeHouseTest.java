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
            coffeeHouse.tell("Brew Coffee", getRef());
            new ExpectMsg<String>("Some [Cc]offee response") {
                @Override
                protected String match(Object msg) {
                    if (msg.toString().matches(".*[Cc]offee.*")) {
                        return "match";
                    } else {
                        throw noMatch();
                    }
                }
            }.get();
        }};
    }

    @Test
    public void shouldLogMessageWhenCreated() {
        new JavaTestKit(system) {{
            interceptDebugLogMessage(this, ".*[Oo]pen.*", 1, () -> system.actorOf(CoffeeHouse.props()));
        }};
    }
}


