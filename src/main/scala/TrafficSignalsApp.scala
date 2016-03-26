import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._

object TrafficSignalsApp extends App {
  val BASE_SIGNAL_TIME = 6
  val system = ActorSystem("TrafficSignalsApp")
  val manager = system.actorOf(SignalManager.props(BASE_SIGNAL_TIME), "SignalManager")
  manager.tell(Initialise, ActorRef.noSender)
}