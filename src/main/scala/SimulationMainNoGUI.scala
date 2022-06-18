import akka.actor.typed.ActorSystem

object SimulationMainNoGUI extends App:
  println("Starting the simulation....")
  val system = ActorSystem(Master(1, 1000, 1000, Option.empty, true), "view")
  system ! Master.Command.Start