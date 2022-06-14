import akka.actor.typed.ActorSystem

object SimulationMain extends App:
  println("Starting the simulation....")
  val system = ActorSystem(Master(10, 100, 10), "master")
  //system ! Master.Command.Start
