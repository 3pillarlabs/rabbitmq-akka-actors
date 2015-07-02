package com.tpg.pnode

import akka.actor.Actor
import akka.stream.actor.{ActorPublisherMessage, ActorPublisher}


case class RabbitMsg(val m: String)

case object GetQueueSize

class RabbitQueueSource extends Actor with ActorPublisher[RabbitMsg] {
  import ActorPublisherMessage._

  var rabbitMsgQueue:List[RabbitMsg] = List.empty

  def receive = {

    case GetQueueSize => sender() ! rabbitMsgQueue.size

    case Request(demand) =>
      if (demand > rabbitMsgQueue.size){
        rabbitMsgQueue foreach (onNext)
        rabbitMsgQueue = List.empty
      }
      else {
        val (send, keep) = rabbitMsgQueue.splitAt(demand.toInt)
        rabbitMsgQueue = keep
        send foreach (onNext)
      }


    case rm:RabbitMsg =>
      if (totalDemand == 0)
        rabbitMsgQueue = rabbitMsgQueue :+ rm
      else
        onNext(rm)
  }


}
