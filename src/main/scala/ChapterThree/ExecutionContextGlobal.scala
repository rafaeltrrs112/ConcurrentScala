package ChapterThree

import ChapterTwo.Exercises.PriorityBasedQueue

import scala.concurrent.ExecutionContext

/**
 *
 */
object ExecutionContextGlobal extends App {
  val ectx = ExecutionContext.global
  ectx.execute(new Runnable {
    def run() = PriorityBasedQueue.log("Running on execution context.")
  })
  Thread.sleep(500)
/**  ExecutionContext : similar to Executor but is more specific to Scala.
  *  -> Many scala methods take ExecutionContext objects as implicit parameters.
  *  -> Implement execute(run Runnable) : Same as executor
  *     reportFailure(cause: Throwable) : Is called whenever some task throws an exception
  */
}
