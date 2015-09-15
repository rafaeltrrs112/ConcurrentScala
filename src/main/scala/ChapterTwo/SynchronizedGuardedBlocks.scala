package ChapterTwo

/**
 * Instead of using busy waiting, use .wait() and .notify()
 * Example
 *  The threads main and greeter use a monitor from a fresh lock object of any type
 *  Both threads run concurrently, with greeter checking if message contains something
 *  within. If message is empty it calls wait on message and goes to sleep until
 *  it is awakened by the call on locks .notify() method which notifies all threads
 *  waiting on lock to wake up and continue. What main is doing is placing a value
 *  into lock and then calling notify().
 *
 *  Guarded Block : A synchronized statement in which some condition is repetitively
 *  checked before calling wait is called a guarded block.
 *
 */
object SynchronizedGuardedBlocks extends App {
  def thread(block : => Unit): Thread ={
    val t = new Thread{
      override def run() = block
    }
    t.start()
    t
  }
  def log(s : String) : Unit = {
    println(Thread.currentThread().getName + " " + s)
  }
  val lock = new AnyRef
  var message : Option[String] = None
  val greeter = thread {
    lock.synchronized{
      //Always use wait in conjunction with a while loop
      //that checks the condition.
      //TODO A thread that checks the condition must acquire the monitor to wake up
      //If it cannot acquire the monitor immediately, it goes into the blocked state.
      while (message.isEmpty) lock.wait()
        log(message.get)
    }
  }
  lock.synchronized{
    message = Some("Hello!")
    lock.notify()
  }
  greeter.join()
}
