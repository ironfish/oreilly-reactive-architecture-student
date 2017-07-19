package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import org.junit.Test;

public class BaristaTest extends BaseAkkaTestCase {

    @Test
    public void sendingPrepareCoffeeShouldResultInCoffeePreparedResponse() {
        new JavaTestKit(system) {{
            ActorRef barista = system.actorOf(Barista.props(duration("100 milliseconds")));
            new Within(duration("50 milliseconds"), duration("1000 milliseconds")) {
                @Override
                protected void run() {
                    barista.tell(new Barista.PrepareCoffee(new Coffee.Akkaccino(), system.deadLetters()), getRef());
                    expectMsgEquals(new Barista.CoffeePrepared(new Coffee.Akkaccino(), system.deadLetters()));
                }
            };
        }};
    }
}
