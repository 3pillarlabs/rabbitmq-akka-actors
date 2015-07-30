package com.tpg.nnode.rabbit;

import akka.japi.pf.ReceiveBuilder;
import akka.stream.actor.AbstractActorPublisher;
import akka.stream.actor.ActorPublisherMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * An actor that acts as the source of our rabbit messages
 */
public class RabbitQueueSourceActor extends AbstractActorPublisher<RabbitQueueSourceActor.RabbitMsg> {

    private final List<RabbitMsg> rabbitMsgQueue = new ArrayList<>();


    public RabbitQueueSourceActor() {
        receive(ReceiveBuilder.
                        match(ActorPublisherMessage.Request.class, request -> {
                            final long demand = request.n();
                            if (demand > rabbitMsgQueue.size()) {
                                rabbitMsgQueue.forEach(rm -> {
                                    onNext(rm);
                                });
                                rabbitMsgQueue.clear();
                            }
                            else {
                                final int splitAt = (int) demand;
                                rabbitMsgQueue.subList(0, splitAt).forEach(rm -> {
                                    onNext(rm);
                                });
                                rabbitMsgQueue.subList(0, splitAt).clear();
                            }

                        }).
                        match(RabbitMsg.class, rabbitMsg -> {
                            if (totalDemand() == 0) {
                                rabbitMsgQueue.add(rabbitMsg);
                            }
                            else {
                                onNext(rabbitMsg);
                            }
                        }).
                        matchAny(o -> {
                                    System.out.println(String.format("Not handling message %s in rabbit source actor", o));
                                }
                        ).build()
        );
    }


    public static class RabbitMsg {
        private final String msg;


        public RabbitMsg(final String msg) {
            this.msg = msg;
        }


        public String getMsg() {
            return msg;
        }


        @Override
        public String toString() {
            return "RabbitMsg{" +
                    "msg='" + msg + '\'' +
                    '}';
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RabbitMsg rabbitMsg = (RabbitMsg) o;

            return !(msg != null ? !msg.equals(rabbitMsg.msg) : rabbitMsg.msg != null);

        }


        @Override
        public int hashCode() {
            return msg != null ? msg.hashCode() : 0;
        }
    }
}
