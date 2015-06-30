package com.tpg.pnode

import scala.language.postfixOps

import akka.actor.{Actor, ActorSystem, Props}

import akka.stream.ActorMaterializer
import akka.stream.actor.ActorPublisher
import akka.stream.scaladsl.{Sink, Source}

import com.github.sstone.amqp.Amqp.{Delivery, Ack}


object ProcessingApp extends App {

  implicit val system = ActorSystem("mySystem")
  implicit val materializer = ActorMaterializer()

  val producer = system.actorOf(Props[RabbitQueueSource])
  val pub = ActorPublisher[RabbitMsg](producer)

  val listener = system.actorOf(Props(new Actor {
    def receive = {
      case Delivery(consumerTag, envelope, properties, body) => {
        val msg = new String(body)
        producer ! RabbitMsg(msg)
        sender ! Ack(envelope.getDeliveryTag)
      }
    }
  }))
  RabbitConn.setUpRabbit(system, listener)

  Source(pub).runWith(Sink.foreach(msg => println(s"sink-ed: $msg")))

  println("press enter to shut down the system...")
  System.in.read()

  system.shutdown()
}
