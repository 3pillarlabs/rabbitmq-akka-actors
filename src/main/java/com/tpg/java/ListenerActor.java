package com.tpg.java;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.github.sstone.amqp.Amqp;
import com.tpg.java.rabbithandler.RabbitQueueSourceActor;


/**
 * Actor binding rabbit to a stream publisher actor
 */
class ListenerActor extends AbstractActor {

    /**
     *
     * @param producer an actor that we will forward the messages to - the actor that will act as the 'Source' of the stream
     */
    public ListenerActor(final ActorRef producer) {
        receive(ReceiveBuilder.
                        match(Amqp.Delivery.class, d -> {
                            String msg = new String(d.body());

                            // tell the stseam publisher that there's a new message available
                            producer.tell(new RabbitQueueSourceActor.RabbitMsg(msg), self());

                            // tell rabbit that we took care of the message
                            sender().tell(new Amqp.Ack(d.envelope().getDeliveryTag()), self());
                        }).
                        matchAny(o -> {
                            System.out.println(String.format("Not handling %s message in rabbit listener actor", o));
                        }).build()
        );

    }

}