package com.lightbend.training.coffeehouse;

import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BaristaTest extends BaseAkkaTestCase {

    @Test
    public void sendingPrepareCoffeeShouldResultInCoffeePreparedResponse() {
        new JavaTestKit(system) {{
            ActorRef barista = system.actorOf(Barista.props(duration("100 milliseconds"), 100));
            new Within(duration("50 milliseconds"), duration("1000 milliseconds")) {
                @Override
                protected void run() {
                    barista.tell(new Barista.PrepareCoffee(new Coffee.Akkaccino(), system.deadLetters()), getRef());
                    expectMsgEquals(new Barista.CoffeePrepared(new Coffee.Akkaccino(), system.deadLetters()));
                }
            };
        }};
    }


    @Test
    public void shouldSendCoffeePreparedWithRandomCoffeeForInaccurateResponse() {
        new JavaTestKit(system) {{
            Integer accuracy = 50;
            Long runs = 1000L;
            ActorRef guest = system.deadLetters();
            ActorRef barista = system.actorOf(Barista.props(duration("0 milliseconds"), accuracy));
            List<Coffee> coffees = new ArrayList<>();
            for (int i = 0; i < runs; i++) {
                barista.tell(new Barista.PrepareCoffee(new Coffee.Akkaccino(), guest), getRef());
                Barista.CoffeePrepared cp = expectMsgClass(duration("50 milliseconds"), Barista.CoffeePrepared.class);
                coffees.add(cp.coffee);
            }
            Long expectedCount = runs * accuracy / 100;
            Long variation = expectedCount / 10;
            Long numberOfCorrectCoffee = coffees.stream().filter(c -> c.equals(new Coffee.Akkaccino())).count();
            assertThat(numberOfCorrectCoffee).isBetween(expectedCount - variation, expectedCount + variation);
        }};
    }
}
