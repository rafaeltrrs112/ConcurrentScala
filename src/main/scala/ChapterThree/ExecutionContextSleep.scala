package ChapterThree

import ChapterTwo.Exercises.PriorityBasedQueue

import scala.concurrent._
/**
 * execute function takes in a block of code
 * by name and runs it to the global ExecutionContext's
 * execute method. This makes the code more concise
 * and doesn't require explicit instantiation of a new ExecutionContext
 *
 * ExecutionContext :  On my dual core core i5 with 4 threads the Execution context
 * creates 4 threads and assigns work to them as the come in from the execute function.
 * ExecuteContext is a much better way to execute concurrent events since creation,
 * and work assignment are done by the Executor object. The Executor also puts threads
 * on wait until they are needed and can gracefully stop them when needed.
 *
 * If four tasks are started using a guarded block idiom, and a separate task is sent out
 * to calling notify() to wake them up, the Executor will not have any threads to
 * execute the notify() action. When threads are unable to begin
 * work again due to waiting on a block that will get released, they are starved.
 * Executing blocking operations on ExecutionContext objects can cause starvation.
 * */
object ExecutionContextSleep extends App {
  def execute(body : => Unit) = ExecutionContext.global.execute(
    new Runnable { def run() = body }
  )
  //
  for(i <- 0 until 32) execute{
    Thread.sleep(2000)
    PriorityBasedQueue.log(s"Task $i completed.")
  }
  Thread.sleep(20000)
}
