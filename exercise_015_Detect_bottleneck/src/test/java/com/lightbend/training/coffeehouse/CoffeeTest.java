package com.lightbend.training.coffeehouse;

import com.google.common.collect.ImmutableSet;
import com.lightbend.training.coffeehouse.Coffee.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static com.lightbend.training.coffeehouse.Coffee.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CoffeeTest {

    @Test
    public void coffeesShouldContains_AkkaccinoCaffeJavaAndMochaPlay() {
        Assertions.assertThat(COFFEES).isEqualTo(ImmutableSet.of(
                new Akkaccino(), new CaffeJava(), new MochaPlay()));
    }

    @Test
    public void orderShouldCreateCorrectBeverageForGivenCode() {
        assertThat(order("A")).isEqualTo(new Akkaccino());
        assertThat(order("a")).isEqualTo(new Akkaccino());
        assertThat(order("C")).isEqualTo(new CaffeJava());
        assertThat(order("c")).isEqualTo(new CaffeJava());
        assertThat(order("M")).isEqualTo(new MochaPlay());
        assertThat(order("m")).isEqualTo(new MochaPlay());
    }

    @Test
    public void orderShouldThrowExceptionForWrongBeverageCode() {
        try {
            order("1");
            fail("Should have raised exception for invalid order code");
        } catch (Exception e) {
            assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void otherDrinkShouldReturnDrinkDifferentFromGivenCode() {
        COFFEES.forEach(c -> assertThat(orderOther(c)).isNotEqualTo(c));
    }
}
