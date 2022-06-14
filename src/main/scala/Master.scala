import akka.actor.typed.{ActorSystem, Behavior, Signal, ActorRef}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object Master:

  enum Command:
    case Start
    case Stop
    case VelocitiesUpdated
    case PositionsUpdated
  export Command.*

  def apply(): Behavior[Command] =
    Behaviors.receive {
      (context, msg) =>
        msg match {
          case Start => ???
          case Stop => Behaviors.stopped
          case VelocitiesUpdated => ???
          case PositionsUpdated => ???
        }
    }

