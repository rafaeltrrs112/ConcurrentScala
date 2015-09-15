package ChapterTwo.Exercises
import scala.annotation.tailrec
import scala.collection.mutable
import scala.annotation.tailrec
class TaskPool(val poolSize : Int) {
  //Set the order for the priority based queue
  implicit def ordered = new Ordering[(Int, () => Unit)] {
    override def compare(x: (Int, () => Unit), y: (Int, () => Unit)): Int = if (x._1 < y._1) x._1 else y._1
  }

  //Priority based task queue stores jobs for the thread pool
  private var tasks = mutable.PriorityQueue[(Int, () => Unit)]()

  //Create an array to hold the pool of worker threads
  val Threads = for (i <- Range(0, poolSize)) yield new Worker()

  //Start all the workers
  for (worker <- Threads) worker.start()

  //Worker inner class
  class Worker extends Thread {

    var terminated = false

    def poll() = tasks.synchronized {
      while (tasks.isEmpty && !terminated) tasks.wait()
      if (!terminated) Some(tasks.dequeue()._2) else None
    }

    @tailrec
    final override def run(): Unit = poll() match {
      case Some(task) => task(); run()
      case None =>
    }

    def shutdown() = tasks.synchronized {
      terminated = true
      tasks.notify()
    }
  }
  def asynchronous(n: Int)(body: => Unit) = tasks.synchronized {
    tasks.enqueue((n, () => body))
    tasks.notify()

  }
  def stopPool() : Unit = tasks.synchronized {
    priorityClear()
    for (worker <- Threads) worker.shutdown()
  }
  def priorityClear() : Unit = {
    tasks = tasks.filter{ (task) =>
      task._1 != 999
    }
    tasks.foreach(println)
  }
}
object PriorityBasedQueue extends App {
  def log(s : String): Unit ={
    println(Thread.currentThread().getName + " " + s)
  }
  val taskPool : TaskPool = new TaskPool(5)
}
