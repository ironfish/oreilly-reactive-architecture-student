package com.lightbend.training.coffeehouse;


import akka.actor.ActorRef;
import akka.testkit.JavaTestKit;
import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CoffeeHouseAppTest extends BaseAkkaTestCase {

    @Test
    public void argsToOptsShouldConvertArgsToOpts() {
        final Map<String, String> result = CoffeeHouseApp.argsToOpts(Arrays.asList("a=1", "b", "-Dc=2"));
        assertThat(result).contains(MapEntry.entry("a", "1"), MapEntry.entry("-Dc", "2"));
    }


    @Test
    public void applySystemPropertiesShouldConvertOptsToSystemProps() {
        System.setProperty("c", "");
        Map<String, String> opts = new HashMap<>();
        opts.put("a", "1");
        opts.put("-Dc", "2");
        CoffeeHouseApp.applySystemProperties(opts);
        assertThat(System.getProperty("c")).isEqualTo("2");
    }

    @Test
    public void shouldCreateATopLevelActorCalledCoffeeHouse() {
        new JavaTestKit(system) {{
            new CoffeeHouseApp(system);
            String path = "/user/coffee-house";
            expectActor(this, path);
        }};
    }

    @Test
    public void shouldCreateNGuestsBasedOnCount() {
        new JavaTestKit(system) {{
            new CoffeeHouseApp(system) {
                @Override
                protected ActorRef createCoffeeHouse() {
                    return getRef();
                }
            }.createGuest(2, new Coffee.Akkaccino(), Integer.MAX_VALUE);
            expectMsgAllOf(new CoffeeHouse.CreateGuest(new Coffee.Akkaccino()),
                    new CoffeeHouse.CreateGuest(new Coffee.Akkaccino()));
        }};

    }
}
