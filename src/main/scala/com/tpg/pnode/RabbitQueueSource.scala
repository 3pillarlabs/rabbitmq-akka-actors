package com.tpg.pnode

import akka.actor.Actor
import akka.stream.actor.{ActorPublisherMessage, ActorPublisher}

import scala.reflect.ClassTag


class RabbitQueueSource extends Actor with ActorPublisher[String] {
  import ActorPublisherMessage._

  var rabbitMsgQueue:List[String] = List.empty

  def receive = {
    
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


    case rabbitMessage:String =>
      if (totalDemand == 0)
        rabbitMsgQueue = rabbitMsgQueue :+ rabbitMessage
      else
        onNext(rabbitMessage)
  }


}
