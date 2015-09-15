package ChapterTwo

/**
 * Using volatile on a variable v ensures that reads and writes on v are
 * guaranteed atomic.
 *
 * TODO: NOTE In most cases however, you should resort to using synchronized statements
 * Volatile semantics are subtle and easy to get wrong.
 */
class Page(val txt: String, var position: Int)
object Volatile extends App{
  def thread(block : => Unit): Thread ={
    val t = new Thread{
      override def run() = block
    }
    t.start()
    t
  }
  val pages = for (i <- 1 to 5) yield
    new Page("Na" * (100 - 20 * i) + " Batman!", -1)
  @volatile var found = false
  for (p <- pages) yield thread {
    var i = 0
    while(i < p.txt.length && !found)
      if(p.txt(i) == '!') {
        p.position = i
        found = true
      } else i += 1
  }
  while(!found){}
  println(Thread.currentThread().getName + s" results: ${pages.map(_.position)}")
}
