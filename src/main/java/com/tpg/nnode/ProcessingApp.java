package com.tpg.nnode;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.ActorMaterializer;
import akka.stream.actor.AbstractActorPublisher;
import akka.stream.javadsl.Source;
import com.tpg.nnode.rabbit.RabbitQueueSourceActor;
import com.tpg.pnode.rules.PasswordRule;
import com.tpg.pnode.rules.Rule;
import com.tpg.pnode.rules.RuleSet;
import com.tpg.pnode.rules.RuleSetBuilder;
import com.tpg.rabbit.RabbitConn;
import org.easyrules.api.RulesEngine;
import org.reactivestreams.Publisher;
import scala.runtime.BoxedUnit;

import java.io.IOException;
import java.util.Set;


public class ProcessingApp {

    public static void main(String[] args) throws IOException {

        ActorSystem aSys = ActorSystem.create("aSys");
        ActorMaterializer aMaterializer = ActorMaterializer.create(aSys);

        ActorRef producer = aSys.actorOf(Props.create(RabbitQueueSourceActor.class), "producer");
        Publisher<RabbitQueueSourceActor.RabbitMsg> pub = AbstractActorPublisher.create(producer);

        final ActorRef listener = aSys.actorOf(Props.create(ListenerActor.class, () -> new ListenerActor(producer)),
                "listener");
        RabbitConn.setUpRabbit(aSys, listener);

        final RuleSet reSet = (new RuleSetBuilder()).add(new PasswordRule()).build();
        final RulesEngine rulesEngine = reSet.getRulesEngine();

        final Source<RabbitQueueSourceActor.RabbitMsg, BoxedUnit> rabbitMessagesSrc = Source.from(pub);
        rabbitMessagesSrc.runForeach(
                m -> {
                    final String rm = m.getMsg();
                    System.out.println(String.format("Sink-ed: %s", rm));
                    reSet.setInput(rm);
                    rulesEngine.fireRules();
                },
                aMaterializer);

        System.in.read();
        aSys.shutdown();

    }
}
