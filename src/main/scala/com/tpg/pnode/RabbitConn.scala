package com.tpg.pnode

import scala.language.postfixOps

import akka.actor.{ActorRef, ActorSystem}

import com.github.sstone.amqp.Amqp.{QueueParameters, AddQueue, QueueBind}
import com.github.sstone.amqp.{Amqp, Consumer, ConnectionOwner}

import com.rabbitmq.client.ConnectionFactory

import scala.concurrent.duration._

object RabbitConn {

  def setUpRabbit(system: ActorSystem, handler: ActorRef) = {
    val connFactory = new ConnectionFactory()
    connFactory.setUri("amqp://guest:guest@localhost/%2F")
    val conn = system.actorOf(ConnectionOwner.props(connFactory, 1 second))

    val consumer = ConnectionOwner.createChildActor(conn, Consumer.props(handler, channelParams = None, autoack = false))
    Amqp.waitForConnection(system, consumer).await()

    consumer ! QueueBind(queue = "rabbitmq-akka-actors", exchange = "amq.direct", routing_key = "mkey")
    consumer ! AddQueue(QueueParameters(name = "rabbitmq-akka-actors", passive = false, durable = true, autodelete = false))
  }

}
