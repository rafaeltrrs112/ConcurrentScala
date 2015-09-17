package sample.hello
import akka.actor.{ActorRef, ActorSystem, Props, Actor}
import scalafx._

case class Number(number : Int)
case class Job()
case class Seat(direction : Int, name: String)
case class Close()
object Worker{
  def props(name : String) : Props = Props(new Worker(name))
}
class Worker(val assignedDoor : String) extends Actor {
  val NORTH = 0
  val SOUTH = 1
  //get count message and print it out
  def receive = {
    case Seat(direction, string) => println("Sending " + string + " to " + assignedDoor)
  }
}
object TrainStation{
  val NORTH = 0
  val SOUTH = 1
}
class TrainStation extends Actor{
  val NORTH = 0
  val SOUTH = 1
  var number = 0
  //A router aka akka executor service maintains a thread/actor pool of
  //4 and assigns them jobs round robin style
  val workerOne = context.actorOf(Worker.props("North"), "northWorker")
  val workerTwo = context.actorOf(Worker.props("South"), "southWorker")
  def receive = {
    case  Seat(direction, name) => direction match {
      case NORTH => workerOne ! Seat(direction, name)
      case SOUTH => workerTwo ! Seat(direction, name)
    }
    case Close =>
      context.system.shutdown()
  }
}
object TrainTestOne extends App {
  val system = ActorSystem("TrainTest")
  val master = system.actorOf(Props(new TrainStation), name = "StationSystem")
  val passengerOne = Seat(TrainStation.NORTH, "Sally")
  val passengerTwo = Seat(TrainStation.SOUTH, "Tom")
  master ! passengerOne
  master ! passengerTwo
  //Create a router that will hold up to four worker threads
  //Create a worker actor that will print out number

}
