package com.tpg.scala

import com.typesafe.config.ConfigFactory

/**
 * Application configuration
 */
object Config {
  val config = ConfigFactory.load()

  /**
   * RabbitMQ configuration
   */
  trait RabbitConfig {
    val rabbitUri = config.getConfig("rabbit").getString("uri")
    val queueName = config.getConfig("rabbit").getString("queue")
  }
}


