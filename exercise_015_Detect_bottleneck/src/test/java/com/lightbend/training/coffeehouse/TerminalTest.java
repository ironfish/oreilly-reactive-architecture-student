package com.lightbend.training.coffeehouse;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TerminalTest {

    @Test
    public void shouldCreateCorrectGuestFromCommand() {
        Assertions.assertThat(Terminal.create("guest")).isEqualTo(new TerminalCommand.Guest(1, new Coffee.Akkaccino(), Integer.MAX_VALUE));
        assertThat(Terminal.create("2 g")).isEqualTo(new TerminalCommand.Guest(2, new Coffee.Akkaccino(), Integer.MAX_VALUE));
        assertThat(Terminal.create("g m")).isEqualTo(new TerminalCommand.Guest(1, new Coffee.MochaPlay(), Integer.MAX_VALUE));
        assertThat(Terminal.create("g 1")).isEqualTo(new TerminalCommand.Guest(1, new Coffee.Akkaccino(), 1));
        assertThat(Terminal.create("2 g m 1")).isEqualTo(new TerminalCommand.Guest(2, new Coffee.MochaPlay(), 1));
    }

    @Test
    public void shouldCreateGetStatusFromCommand() {
        assertThat(Terminal.create("status")).isEqualTo(TerminalCommand.Status.Instance);
        assertThat(Terminal.create("s")).isEqualTo(TerminalCommand.Status.Instance);

    }

    @Test
    public void shouldCreateQuitFromCommand() {
        assertThat(Terminal.create("quit")).isEqualTo(TerminalCommand.Quit.Instance);
        assertThat(Terminal.create("q")).isEqualTo(TerminalCommand.Quit.Instance);
    }

    @Test
    public void shouldCreateUnknownFromCommand() {
        assertThat(Terminal.create("foo")).isEqualTo(new TerminalCommand.Unknown("foo"));
    }
}
