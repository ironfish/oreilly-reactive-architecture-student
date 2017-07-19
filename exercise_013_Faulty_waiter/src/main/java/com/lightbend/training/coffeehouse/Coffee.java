/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */
package com.lightbend.training.coffeehouse;

import com.google.common.collect.ImmutableSet;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public interface Coffee extends Serializable {

    long serialVersionUID = 1;
    // NOTE return copy of static list???
    ImmutableSet<Coffee> COFFEES = ImmutableSet.of(
            new Akkaccino(), new CaffeJava(), new MochaPlay());

    static Coffee order(final String code) {
        switch (code.toLowerCase()) {
            case Akkaccino.CODE:
                return new Akkaccino();
            case MochaPlay.CODE:
                return new MochaPlay();
            case CaffeJava.CODE:
                return new CaffeJava();
            default:
                throw new IllegalArgumentException(String.format("Unknown drink code \"%s\"!", code));
        }
    }

    static Coffee orderOther(final Coffee coffee) {
        Random rnd = new Random();
        List<Coffee> filtered = COFFEES.stream().filter(c -> !c.equals(coffee)).collect(Collectors.toList());
        return filtered.get(rnd.nextInt(filtered.size()));
    }

    final class Akkaccino implements Coffee {

        public static final String CODE = "a";
        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "{}";
        }

        @Override
        public boolean equals(Object o) {
            return o == this || o instanceof Akkaccino;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    final class CaffeJava implements Coffee {

        public static final String CODE = "c";
        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "{}";
        }

        @Override
        public boolean equals(Object o) {
            return o == this || o instanceof CaffeJava;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    final class MochaPlay implements Coffee {

        public static final String CODE = "m";
        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "{}";
        }

        @Override
        public boolean equals(Object o) {
            return o == this || o instanceof MochaPlay;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }
}
