package com.tpg.pnode

import akka.actor.{ActorRef, Actor}
import com.github.sstone.amqp.Amqp.{Ack, Delivery}
import com.tpg.pnode.rabbit.RabbitQueueSource.RabbitMsg

/**
 * Actor binding rabbit to a stream publisher actor
 * @param producer an actor that we will forward the messages to - the actor that will act as the 'Source' of the stream
 */
class ListenerActor(producer: ActorRef) extends Actor {

  def receive = {
    case Delivery(consumerTag, envelope, properties, body) => {
      val msg = new String(body)

      // tell the stream publisher that there's a new message available
      producer ! RabbitMsg(msg)

      // tell rabbit that we took care of the message
      sender ! Ack(envelope.getDeliveryTag)
    }
    case o => println("Not handling %s message in rabbit listener actor".format(o))
  }

}
