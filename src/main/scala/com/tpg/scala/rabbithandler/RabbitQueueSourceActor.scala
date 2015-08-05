package com.tpg.scala.rabbithandler

import akka.actor.Actor
import akka.stream.actor.{ActorPublisher, ActorPublisherMessage}

object RabbitQueueSource {

  /**
   * wraps a rabbit message
   * @param m the message itself
   */
  case class RabbitMsg(val m: String)


  /**
   * we send this to [[com.tpg.scala.rabbithandler.RabbitQueueSource.RabbitQueueSourceActor]] to get its queue size back
   */
  case object GetQueueSize


  /** An actor that acts as the source of our rabbit messages */
  class RabbitQueueSourceActor extends Actor with ActorPublisher[RabbitMsg] {

    import ActorPublisherMessage._

    var rabbitMsgQueue: List[RabbitMsg] = List.empty

    def receive = {

      case GetQueueSize => sender() ! rabbitMsgQueue.size

      case Request(demand) =>
        if (demand > rabbitMsgQueue.size) {
          rabbitMsgQueue foreach (onNext)
          rabbitMsgQueue = List.empty
        }
        else {
          val (send, keep) = rabbitMsgQueue.splitAt(demand.toInt)
          rabbitMsgQueue = keep
          send foreach (onNext)
        }


      case rm: RabbitMsg =>
        if (totalDemand == 0)
          rabbitMsgQueue = rabbitMsgQueue :+ rm
        else
          onNext(rm)
          
      case o => println("Not handling message %s in rabbit source actor".format(o))
    }


  }

}

