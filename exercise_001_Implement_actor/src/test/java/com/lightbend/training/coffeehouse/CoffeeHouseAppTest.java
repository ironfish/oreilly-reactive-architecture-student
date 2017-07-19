package com.lightbend.training.coffeehouse;


import org.assertj.core.data.MapEntry;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CoffeeHouseAppTest {

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
}
