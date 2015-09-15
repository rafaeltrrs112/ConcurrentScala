package ChapterTwo

/**
 * Thread pool : A set of reusable threads.
 */

import scala.annotation.tailrec
import scala.collection._
object SynchronizedPool extends App {
  //Mutable queue stores scheduled blocks of code
  private val tasks = mutable.Queue[() => Unit]()
  object Worker extends Thread {
    var terminated = false
    def poll() = tasks.synchronized {
      //If there is no task and I have not been told to terminated
      //then I will wait on tasks until someone calls notify on it.
      while (tasks.isEmpty && !terminated) tasks.wait()
      if(!terminated) Some(tasks.dequeue()) else None
    }
    @tailrec
    override def run() = poll() match {
      case Some(task) => task() ; run()
      case None =>
    }
    def shutdown() = tasks.synchronized {
      terminated = true
      tasks.notify()
    }
  Worker.start() //Starts the worker Daemon
}
  //Adds a action to the queue when it can obtain the monitor for tasks.
  def asynchronous(body : => Unit) = tasks.synchronized {
    //Add the task to the queue and notify anyone waiting on this object
    tasks.enqueue(() => body)
    tasks.notify()
  }
  asynchronous(println("Hello"))
  asynchronous(println("world!"))
  asynchronous(Worker.shutdown())
  Thread.sleep(500)
  }
