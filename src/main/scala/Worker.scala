import akka.actor.typed.{ActorSystem, Behavior, Signal, ActorRef}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object Worker:

  enum Command:
    case UpdateVelocities
    case UpdatePositions
    case Stop
  export Command.*

  def apply(): Behavior[Command] =
    Behaviors.receive {
      (context, msg) =>
        msg match{
          case UpdateVelocities => ???
          case UpdatePositions => ???
          case Stop => Behaviors.stopped
        }
    }