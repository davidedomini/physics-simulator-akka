import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Signal}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import lib.V2d
import java.util.ArrayList

object WorkerActor:
  enum Command:
    case UpdateVelocities(task: Task, replyTo: ActorRef[Master.Command])
    case UpdatePositions(task: Task, replyTo: ActorRef[Master.Command])
    case Stop
  export Command.*

  def apply(): Behavior[Command] =
    Behaviors.receive {
      (context, msg) =>
        msg match {
          case UpdateVelocities(task, replyTo) =>
            val stop = task.getStart+task.getBodiesForWorker
            for 
              i <- task.getStart to stop
              if i < task.getBodies.size()
              b = task.getBodies.get(i)
              totalForce = Utility.computeTotalForceOnBody(b, task.getBodies)
              acc = new V2d(totalForce).scalarMul(1.0 / b.getMass)
            yield b.updateVelocity(acc, 0.01)
            replyTo ! Master.Command.VelocitiesUpdated(TaskResult(new ArrayList(task.getBodies.subList(task.getStart, stop))))
            Behaviors.same
          case UpdatePositions(task, replyTo) => Behaviors.same
          case Stop =>
            println("Mi è arrivato uno stop")
            Behaviors.stopped
        }
    }