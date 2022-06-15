import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Signal}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import lib.{Boundary, V2d}

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
            val stop = task.start + task.bodiesForWorker
            for
              i <- task.start to stop
              if i < task.bodies.size()
              b = task.bodies.get(i)
              totalForce = Utility.computeTotalForceOnBody(b, task.bodies)
              acc = new V2d(totalForce).scalarMul(1.0 / b.getMass)
            yield b.updateVelocity(acc, 0.01)
            replyTo ! Master.Command.VelocitiesUpdated(TaskResult(new ArrayList(task.bodies.subList(task.start, stop))))
            Behaviors.same
          case UpdatePositions(task, replyTo) =>
            val stop = task.start + task.bodiesForWorker
            for
              i <- task.start to stop
              if i < task.bodies.size()
              b = task.bodies.get(i)
            do 
              b.updatePos(0.01)
              b.checkAndSolveBoundaryCollision(task.bounds)

            replyTo ! Master.Command.PositionsUpdated(TaskResult(new ArrayList(task.bodies.subList(task.start, stop))))
            Behaviors.same
          case Stop =>
            Behaviors.stopped
        }
    }