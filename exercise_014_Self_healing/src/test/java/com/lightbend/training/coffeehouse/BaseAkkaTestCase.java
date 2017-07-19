package com.lightbend.training.coffeehouse;

import akka.actor.*;
import akka.event.Logging;
import akka.japi.Creator;
import akka.testkit.EventFilter;
import akka.testkit.JavaTestKit;
import akka.testkit.TestEvent;
import akka.testkit.TestProbe;
import org.junit.After;
import org.junit.Before;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public abstract class BaseAkkaTestCase {

    protected ActorSystem system = ActorSystem.create();

    @Before
    public void setUp() {
        final EventFilter defaultDebug = EventFilter.debug(null, null, "", null, Integer.MAX_VALUE);
        final EventFilter defaultInfo = EventFilter.info(null, null, "", null, Integer.MAX_VALUE);
        final EventFilter defaultWarning = EventFilter.warning(null, null, "", null, Integer.MAX_VALUE);
        final EventFilter defaultError = EventFilter.error(null, null, "", null, Integer.MAX_VALUE);

        system.eventStream().publish(new TestEvent.Mute(Arrays.asList(defaultDebug)));
        system.eventStream().publish(new TestEvent.Mute(Arrays.asList(defaultInfo)));
        system.eventStream().publish(new TestEvent.Mute(Arrays.asList(defaultWarning)));
        system.eventStream().publish(new TestEvent.Mute(Arrays.asList(defaultError)));
    }

    public void interceptInfoLogMessage(JavaTestKit kit, String pattern, int occurrences, final CodeUnderInspection i) {
        eventFilter(kit, Logging.Info.class, pattern, occurrences, i);
    }

    public void interceptDebugLogMessage(JavaTestKit kit, String pattern, int occurrences, final CodeUnderInspection i) {
        eventFilter(kit, Logging.Debug.class, pattern, occurrences, i);
    }

    public void interceptErrorLogMessage(JavaTestKit kit, String pattern, int occurrences, final CodeUnderInspection i) {
        eventFilter(kit, Logging.Error.class, pattern, occurrences, i);
    }

    public void eventFilter(JavaTestKit kit, Class clazz, String pattern, int occurrences, final CodeUnderInspection i) {
        kit.new EventFilter<Void>(clazz) {
            protected Void run() {
                i.run();
                return null;
            }
        }.matches(pattern).occurrences(occurrences).exec();

    }

    public ActorRef expectActor(JavaTestKit kit, String path) {
        final ActorRef[] actor = {null};
        kit.new AwaitCond(kit.duration("3 seconds"), kit.duration("150 millis"), "No actor found with " + path) {
            @Override
            protected boolean cond() {
                TestProbe probe = new TestProbe(system);
                system.actorSelection(path).tell(new akka.actor.Identify(101), probe.ref());
                ActorIdentity i = probe.expectMsgClass(kit.duration("100 millis"), ActorIdentity.class);
                actor[0] = i.getRef();
                return i.getRef() != null;
            }
        };
        return actor[0];
    }

    public <T extends AbstractActor> ActorRef createActor(Class<T> clazz, String name, Creator<T> factory) {
        Props stub = Props.create(clazz, factory);
        return system.actorOf(stub, name);
    }

    public ActorRef createStubActor(String name, Creator<AbstractActor> factory) {
        return createActor(AbstractActor.class, name, factory);
    }

    @After
    public void tearDown() throws TimeoutException, InterruptedException {
        Await.ready(system.terminate(), Duration.Inf());
    }

    @FunctionalInterface
    public static interface CodeUnderInspection {
        public void run();
    }
}
