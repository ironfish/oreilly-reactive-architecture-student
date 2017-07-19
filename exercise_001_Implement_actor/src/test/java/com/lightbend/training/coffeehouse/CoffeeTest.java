package com.lightbend.training.coffeehouse;

import com.google.common.collect.ImmutableSet;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CoffeeTest {

    @Test
    public void coffeesShouldContains_AkkaccinoCaffeJavaAndMochaPlay() {
        assertThat(Coffee.COFFEES).isEqualTo(ImmutableSet.of(
                new Coffee.Akkaccino(), new Coffee.CaffeJava(), new Coffee.MochaPlay()));
    }

    @Test
    public void orderShouldCreateCorrectBeverageForGivenCode() {
        Assertions.assertThat(Coffee.order("A")).isEqualTo(new Coffee.Akkaccino());
        Assertions.assertThat(Coffee.order("a")).isEqualTo(new Coffee.Akkaccino());
        Assertions.assertThat(Coffee.order("C")).isEqualTo(new Coffee.CaffeJava());
        Assertions.assertThat(Coffee.order("c")).isEqualTo(new Coffee.CaffeJava());
        Assertions.assertThat(Coffee.order("M")).isEqualTo(new Coffee.MochaPlay());
        Assertions.assertThat(Coffee.order("m")).isEqualTo(new Coffee.MochaPlay());
    }

    @Test
    public void orderShouldThrowExceptionForWrongBeverageCode() {
        try {
            Coffee.order("1");
            fail("Should have raised exception for invalid order code");
        } catch (Exception e) {
            assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void otherDrinkShouldReturnDrinkDifferentFromGivenCode() {
        Coffee.COFFEES.forEach(c -> Assertions.assertThat(Coffee.orderOther(c)).isNotEqualTo(c));
    }
}
