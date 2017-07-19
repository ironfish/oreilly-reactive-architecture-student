/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */
package com.lightbend.training.coffeehouse;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

public interface TerminalCommand extends Serializable {

    long serialVersionUID = 1;

    final class Guest implements TerminalCommand {

        private static final long serialVersionUID = 1L;

        public final int count;

        public final Coffee coffee;

        public final int maxCoffeeCount;

        public Guest(final int count, final Coffee coffee, final int maxCoffeeCount) {
            checkNotNull(coffee, "Coffee cannot be null");
            this.count = count;
            this.coffee = coffee;
            this.maxCoffeeCount = maxCoffeeCount;
        }

        @Override
        public String toString() {
            return "Guest{"
                    + "count=" + count + ", "
                    + "coffee=" + coffee + ", "
                    + "maxCoffeeCount=" + maxCoffeeCount
                    + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof Guest) {
                Guest that = (Guest) o;
                return (this.count == that.count)
                        && (this.coffee.equals(that.coffee))
                        && (this.maxCoffeeCount == that.maxCoffeeCount);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = 1;
            h *= 1000003;
            h ^= count;
            h *= 1000003;
            h ^= coffee.hashCode();
            h *= 1000003;
            h ^= maxCoffeeCount;
            return h;
        }
    }

    final class Status implements TerminalCommand {

        public static final Status Instance = new Status();
        private static final long serialVersionUID = 1L;

        private Status() {
        }
    }

    final class Quit implements TerminalCommand {

        public static final Quit Instance = new Quit();
        private static final long serialVersionUID = 1L;

        private Quit() {
        }
    }

    final class Unknown implements TerminalCommand {

        private static final long serialVersionUID = 1L;

        public final String command;

        public Unknown(final String command) {
            checkNotNull(command, "Command cannot be null");
            this.command = command;
        }

        @Override
        public String toString() {
            return "Unknown{command=" + command + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof Unknown) {
                Unknown that = (Unknown) o;
                return (this.command.equals(that.command));
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = 1;
            h *= 1000003;
            h ^= command.hashCode();
            return h;
        }
    }
}
