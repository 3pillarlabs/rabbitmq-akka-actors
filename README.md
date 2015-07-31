# rabbitmq-akka-actors

Typesafe's Activator template which exposes the building blocks for a distributed system in which components communicate via a message broker (rabbitmq) and process messages using akka actors.

The template should be useful in building systems for batch processing that are designed around a workflow where information has to be passed around all the nodes so that each can augment it with specifics.

Each node can scale by making use of akka streams. The message broker provides connectivity between the nodes and allows to keep the business logic for each node to a minimum - thus paving the way for the system to adhere to the SOC principle.

# About rabbitmq-akka-actors

![3Pillar Global] (http://www.3pillarglobal.com/wp-content/themes/base/library/images/logo_3pg.png)

This Activator template is maintained by [http://www.3pillarglobal.com/].
