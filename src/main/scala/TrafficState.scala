import java.util.concurrent.TimeUnit
import scala.concurrent.duration._

trait TrafficState {
  def next: TrafficState

  def north: ChangeTo
  def south: ChangeTo
  def east: ChangeTo
  def west: ChangeTo

  def duration(baseTime: Int): FiniteDuration

  val STANDBY_TIME = 2
}

case object StandBy extends TrafficState {
  def next = NorthSouthStraight

  def north = ChangeTo(Red, Red, Red)
  def south = ChangeTo(Red, Red, Red)
  def east = ChangeTo(Red, Red, Red)
  def west = ChangeTo(Red, Red, Red)

  def duration(baseTime: Int) = new FiniteDuration(STANDBY_TIME, TimeUnit.SECONDS)

  override def toString = "StandBy"
}

case object NorthSouthStraight extends TrafficState {
  def next = NorthSouthStraightStandBy

  def north = ChangeTo(Red, Green, Red)
  def south = ChangeTo(Red, Green, Red)
  def east = ChangeTo(Red, Red, Red)
  def west = ChangeTo(Red, Red, Red)

  def duration(baseTime: Int) = new FiniteDuration(baseTime, TimeUnit.SECONDS)

  override def toString = "NorthSouthStraight"
}

case object NorthSouthStraightStandBy extends TrafficState {
  def next = NorthSouthRight

  def north = ChangeTo(Red, Amber, Red)
  def south = ChangeTo(Red, Amber, Red)
  def east = ChangeTo(Red, Red, Red)
  def west = ChangeTo(Red, Red, Red)

  def duration(baseTime: Int) = new FiniteDuration(STANDBY_TIME, TimeUnit.SECONDS)

  override def toString = "NorthSouthStraightStandBy"
}

case object NorthSouthRight extends TrafficState {
  def next = NorthSouthRightStandBy

  def north = ChangeTo(Red, Red, Green)
  def south = ChangeTo(Red, Red, Green)
  def east = ChangeTo(Green, Red, Red)
  def west = ChangeTo(Green, Red, Red)

  def duration(baseTime: Int) = new FiniteDuration(baseTime / 2, TimeUnit.SECONDS)

  override def toString = "NorthSouthRight"
}

case object NorthSouthRightStandBy extends TrafficState {
  def next = EastWestStraight

  def north = ChangeTo(Red, Red, Amber)
  def south = ChangeTo(Red, Red, Amber)
  def east = ChangeTo(Amber, Red, Red)
  def west = ChangeTo(Amber, Red, Red)

  def duration(baseTime: Int) = new FiniteDuration(STANDBY_TIME, TimeUnit.SECONDS)

  override def toString = "NorthSouthRightStandBy"
}

case object EastWestStraight extends TrafficState {
  def next = EastWestStraightStandBy

  def north = ChangeTo(Red, Red, Red)
  def south = ChangeTo(Red, Red, Red)
  def east = ChangeTo(Red, Green, Red)
  def west = ChangeTo(Red, Green, Red)

  def duration(baseTime: Int) = new FiniteDuration(baseTime, TimeUnit.SECONDS)

  override def toString = "EastWestStraight"
}

case object EastWestStraightStandBy extends TrafficState {
  def next = EastWestRight

  def north = ChangeTo(Red, Red, Red)
  def south = ChangeTo(Red, Red, Red)
  def east = ChangeTo(Red, Amber, Red)
  def west = ChangeTo(Red, Amber, Red)

  def duration(baseTime: Int) = new FiniteDuration(STANDBY_TIME, TimeUnit.SECONDS)

  override def toString = "EastWestStraightStandBy"
}

case object EastWestRight extends TrafficState {
  def next = EastWestRightStandBy

  def north = ChangeTo(Green, Red, Red)
  def south = ChangeTo(Green, Red, Red)
  def east = ChangeTo(Red, Red, Green)
  def west = ChangeTo(Red, Red, Green)

  def duration(baseTime: Int) = new FiniteDuration(baseTime / 2, TimeUnit.SECONDS)

  override def toString = "EastWestRight"
}

case object EastWestRightStandBy extends TrafficState {
  def next = NorthSouthStraight

  def north = ChangeTo(Amber, Red, Red)
  def south = ChangeTo(Amber, Red, Red)
  def east = ChangeTo(Red, Red, Amber)
  def west = ChangeTo(Red, Red, Amber)

  def duration(baseTime: Int) = new FiniteDuration(STANDBY_TIME, TimeUnit.SECONDS)

  override def toString = "EastWestRightStandBy"
}