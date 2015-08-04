package com.tpg.pnode

import akka.actor.{Actor, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.actor.ActorPublisher
import akka.stream.scaladsl.{Sink, Source}
import com.github.sstone.amqp.Amqp.{Ack, Delivery}
import com.tpg.pnode.rabbit.RabbitQueueSource.{RabbitMsg, RabbitQueueSourceActor}
import com.tpg.pnode.rules.{PasswordRule, RuleSetBuilder}
import com.tpg.rabbit.RabbitConn

import scala.language.postfixOps


object ProcessingApp extends App {

  implicit val aSys = ActorSystem("aSys")
  implicit val aMaterializer = ActorMaterializer()

  val producer = aSys.actorOf(Props[RabbitQueueSourceActor])
  val pub = ActorPublisher[RabbitMsg](producer)

  val listener = aSys.actorOf(Props(new ListenerActor(producer)))
  RabbitConn.setUpRabbit(aSys, listener)

  val reSet = (new RuleSetBuilder).add(new PasswordRule).build()
  val rulesEngine = reSet.getRulesEngine

  Source(pub).runWith(Sink.foreach
    (msg => {
      println(s"sink-ed: $msg")
      reSet.setInput(msg.m)
      rulesEngine.fireRules
    })
  )

}
