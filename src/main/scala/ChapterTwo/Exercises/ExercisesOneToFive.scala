package ChapterTwo.Exercises
/**
 * Exercises one to five stored here
 */
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
class ValueThread[A](block : => A) extends Thread {
  var result : Option[A] = None
  override def run() : Unit = {
    result = Some(block)
  }
}
object ExercisesOneToFive extends App {
  def thread(block : => Unit): Thread ={
    val t = new Thread{
      override def run() = block
    }
    t.start()
    t
  }
  def parallel[A, B](a: => A, b: => B): (A, B) = {
//    val runA : Future[A] = Future {
//      a
//    }
//    val runB : Future[B] = Future {
//      b
//    }
//    val pairResult : Future[(A,B)] = for {
//      first <- runA
//      second <- runB
//    } yield (first, second)
//    Await.result(pairResult, Duration.Inf)
    val T1 = new ValueThread[A](a)
    val T2 = new ValueThread[B](b)
    T1.start()
    T2.start()
    T1.join()
    T2.join()
    (T1.result.get, T2.result.get)
  }
  def periodically(duration: Long)(b: =>Unit): Unit = {
    while(true) {
      thread(b)
      Thread.sleep(duration * 1000)
    }
  }
  val three = periodically(3){
    println("Go Home roger!")
  }
}
