import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Signal}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import model.SimulationModel

import java.util.ArrayList

object Master:

  enum Command:
    case Start
    case Stop
    case VelocitiesUpdated
    case PositionsUpdated
  export Command.*

  def apply(nWorkers: Int, nBodies: Int, totalIter: Int): Behavior[Command] =

    var workers = Seq.empty[ActorRef[WorkerActor.Command]]
    val model: SimulationModel = new SimulationModel(nBodies, totalIter);
    val bodiesForWorker = Math.ceil((nBodies / nWorkers).toDouble).toInt
    
    Behaviors.receive {
      (context, msg) =>
        msg match {
          case Start =>
            model.init()
            workers = for{
                i <- 1 to nWorkers
            } yield  context.spawn(WorkerActor(), "Worker" + i)
              for
                w <- workers
                i = workers.indexOf(w)
                start = i * bodiesForWorker
              yield w ! WorkerActor.UpdateVelocities(Task(new ArrayList(model.getBodies), start, bodiesForWorker), context.self)
            Behaviors.same
          case Stop => Behaviors.stopped
          case VelocitiesUpdated => Behaviors.same
          case PositionsUpdated => Behaviors.same
        }
    }

