import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._

import java.util.Date
import java.text.SimpleDateFormat

abstract class SignalMessage
case object Initialise extends SignalMessage
case object Next extends SignalMessage
case class ChangeTo(val lc: Color, val sc: Color, val rc: Color) extends SignalMessage
case object Print extends SignalMessage

class SignalManager(val baseTime: Int) extends Actor {

  private val northSignal = createSignal("North")
  private val southSignal = createSignal("South")
  private val eastSignal = createSignal("East")
  private val westSignal = createSignal("West")

  private var state: TrafficState = StandBy

  private def createSignal(name: String) = {
    context.actorOf(Signal.props(name), name)
  }

  private def changeState(nextState: TrafficState) = {
    this.northSignal ! nextState.north
    this.southSignal ! nextState.south
    this.eastSignal  ! nextState.east
    this.westSignal  ! nextState.west
    this.state = nextState

    // Trigger next change
    context.system.scheduler.scheduleOnce(
      this.state.duration(this.baseTime), 
      self, 
      Next
    )(context.system.dispatcher, self)
    
    self ! Print
  }

  private def printTrafficState() = {
    println(timeHeader + " -> " + this.state)
    println(directionHeader)
    this.northSignal ! Print
    this.southSignal ! Print
    this.eastSignal  ! Print
    this.westSignal  ! Print
  }

  private def timeHeader: String = {
    val now = new Date
    val format = new SimpleDateFormat("HH:mm:ss")
    "Time: " + format.format(now)
  }

  private def directionHeader: String = "                    Left   Straight Right"

  def receive = {
    case Initialise => changeState(StandBy)
    case Next => changeState(this.state.next)
    case Print => printTrafficState()
    case _ => throw new Exception("Unsupported message to SignalManager")
  }

}

object SignalManager {
  def props(time: Int): Props = Props(new SignalManager(time))
}