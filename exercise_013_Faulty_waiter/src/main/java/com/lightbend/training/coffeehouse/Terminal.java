/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */
package com.lightbend.training.coffeehouse;

import com.lightbend.training.coffeehouse.TerminalCommand.Guest;
import com.lightbend.training.coffeehouse.TerminalCommand.Quit;
import com.lightbend.training.coffeehouse.TerminalCommand.Status;
import com.lightbend.training.coffeehouse.TerminalCommand.Unknown;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Terminal {

    Pattern createGuestPattern = Pattern.compile("(\\d+)?\\s*(?:guest|g)\\s*(A|a|M|m|C|c)?\\s*(\\d+)?");
    Pattern getStatusPattern = Pattern.compile("status|s");
    Pattern quitPattern = Pattern.compile("quit|q");

    static TerminalCommand create(final String s) {

        final Matcher guestMatcher = createGuestPattern.matcher(s);
        if (guestMatcher.matches()) {

            final String countGroup = guestMatcher.group(1);
            final int count = countGroup != null ? Integer.parseInt(countGroup) : 1;

            final String coffeeGroup = guestMatcher.group(2);
            final Coffee coffee = coffeeGroup != null ? Coffee.order(coffeeGroup) : new Coffee.Akkaccino();

            final String maxCoffeeCountGroup = guestMatcher.group(3);
            final int maxCoffeeCount =
                    maxCoffeeCountGroup != null ? Integer.parseInt(maxCoffeeCountGroup) : Integer.MAX_VALUE;

            return new Guest(count, coffee, maxCoffeeCount);
        }
        if (getStatusPattern.matcher(s).matches()) return Status.Instance;
        if (quitPattern.matcher(s).matches()) return Quit.Instance;
        return new Unknown(s);
    }
}
