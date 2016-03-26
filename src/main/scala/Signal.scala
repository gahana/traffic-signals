import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._

abstract class Color
case object Red extends Color
case object Amber extends Color
case object Green extends Color

abstract class Direction
case class Left(var color: Color) extends Direction
case class Straight(var color: Color) extends Direction
case class Right(var color: Color) extends Direction

class Signal(val name: String) extends Actor {

  private var left: Left = new Left(Amber)
  private var straight: Straight = new Straight(Amber)
  private var right: Right = new Right(Amber)

  def receive = {
    case Initialise => changeColors(Amber, Amber, Amber)
    case ChangeTo(l, s, r) => changeColors(l, s, r)
    case Print => println(this.toString)
    case _ => throw new Exception("Unsupported message to Signal: " + name)
  }

  def changeColors(lc: Color, sc: Color, rc: Color) = {
    this.left.color = lc
    this.straight.color = sc
    this.right.color = rc
  }

  override def toString = {
    val sigName = this.name
    val leftColor = color(left.color)
    val straightColor = color(straight.color)
    val rightColor = color(right.color)
    f"Traffic from $name%5s    $leftColor       $straightColor       $rightColor"
  }

  private def color(c: Color): String = c match {
    case Red => "R"
    case Amber => "A"
    case Green => "G"
    case _ => "-"
  }
  
}

object Signal {
  def props(name: String): Props = Props(new Signal(name))
}
