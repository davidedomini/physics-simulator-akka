import akka.actor.typed.{ActorSystem, Behavior, Signal, ActorRef}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object ViewActor:

  enum Command:
    case UpdateView
  export Command.*

  def apply(): Behavior[Command] =
    Behaviors.receive {
      (context, msg) =>
        msg match{
          case UpdateView => ???
        }
    }