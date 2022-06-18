import akka.actor.typed.ActorSystem

object SimulationMainNoGUI extends App:
  println("Starting the simulation....")
  val system = ActorSystem(Master(10, 5000, 1000, Option.empty), "view")
  system ! Master.Command.Start
