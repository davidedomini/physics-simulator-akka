import java.util.ArrayList
import lib.Body
import lib.Boundary

case class Task(bodies: ArrayList[Body], start: Int,bodiesForWorker: Int, bounds: Boundary)