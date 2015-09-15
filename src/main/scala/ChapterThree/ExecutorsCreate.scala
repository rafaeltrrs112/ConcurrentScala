package ChapterThree
import scala.concurrent._
import scala.concurrent.forkjoin.ForkJoinPool
import java.util.concurrent.TimeUnit
import ChapterTwo.Exercises.PriorityBasedQueue.log
object ExecutorsCreate extends App {
  val executor = new ForkJoinPool()
  executor.execute(new Runnable {
    override def run(): Unit = {
      log("This task is run asynchronously")
    }
  })

  //To ensure that main doesn't terminate before executor is done
  executor.shutdown()
  executor.awaitTermination(60, TimeUnit.SECONDS)

  /**
   * Executors take an object of type Runnable that implements a method
   * run. The run method contains the action to execute. The Executor will
   * execute this task in one of many pre-created threads in it's pool.
   *
   * Why use Executors : The programmer can focus on specifying parts of the code that
   * potentially execute concurrently, separately from where and when to execute
   * those parts of the code.
   *
   * ExecutorService extends Executor : An extended Executor interface that defines
   * several convenience methods :
   *    shutdown() : Executes all remaining tasks and then stops all the worker threads.
   *      NOTE : Be sure to call shutdown before program ends.
   *
   *
   **/
}
