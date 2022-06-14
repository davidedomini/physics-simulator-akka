import akka.actor.typed.{ActorSystem, Behavior, Signal, ActorRef}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object Master:

  enum Command:
    case Start
    case Stop
    case VelocitiesUpdated
    case PositionsUpdated
  export Command.*

  def apply(nWorkers: Int, nBodies: Int): Behavior[Command] =
    
    var workers = Seq.empty[ActorRef[Worker.Command]]
    
    Behaviors.receive {
      (context, msg) =>
        msg match {
          case Start => 
            workers = 
              for 
                i <- 1 to nWorkers
              yield  context.spawn(Worker(), "Worker" + i)
            Behaviors.same
          case Stop => Behaviors.stopped
          case VelocitiesUpdated => ???
          case PositionsUpdated => ???
        }
    }

