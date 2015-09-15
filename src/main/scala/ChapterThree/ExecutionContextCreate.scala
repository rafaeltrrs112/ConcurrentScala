package ChapterThree
import ChapterTwo.Exercises.PriorityBasedQueue
import scala.concurrent._
import scala.concurrent.forkjoin.ForkJoinPool

object ExecutionContextCreate extends App {

  //This forkjoin instance will usually keep two workers threads in its pool.
  val pool = new ForkJoinPool(2)
  //Creates an execution context from an Executor service
  val ectx = ExecutionContext.fromExecutorService(pool)
  ectx.execute(new Runnable {
    def run() = PriorityBasedQueue.log("Running on the execution context again.")
  })
  Thread.sleep(500)
}
