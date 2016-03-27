# Traffic Signals App
## Programming Problem
1. Think of the traffic signal at a 2-way intersection of roads (in the shape of +). Traffic is coming into the intersection from 4 directions (north, east, south and west) and can leave in all 4 directions. Assume Indian left-hand-drive convention. All the 4-incoming roads have a signal in the usual red-amber-green form. The challenge is to write a program that controls the signals at the junction. There is NO free-left and NO free-right. So there are 3 sets of signal for traffic coming in each direction: one set for going straight, one set for going left and one set for going right. Assume no lane has priority at this signal. Signals should change with the following time pattern – Green for X-secs followed by Amber for 10 secs followed by Red for Y-secs. ‘X’ is the input to the program, ‘Y’ should be computed by the program. Program should be optimised for collision-free ‘maximum' traffic flow — that is, you cannot give Green to all three directions in one lane (go-straight, go-left and go-right) and block all other lanes from all other directions — this will be sub-optimal solution. Maximum flow happens when you open up opposite sides together. So think hard about how you want the transitions to happen before you start to code
2. When you think of traffic at this junction, please consider the traffic going straight (north-south and east-west) to be double of the traffic that makes a turn (north-east, north-west, south-east etc). So the time the signal is GREEN for turning traffic should be half of the traffic going straight. So the input ‘X’ is the number of seconds for GREEN for traffic going straight — and (X/2) would be the time it is GREEN for traffic which is making a turn. 
3. The output of the program should be a continuously logging matrix of set of signals at each junction with timestamp at the top

```
Time: 09:00:00
                    Left  Straight  Right
Traffic from North  R/A/G   R/A/G   R/A/G
Traffic from East   R/A/G   R/A/G   R/A/G
Traffic from South  R/A/G   R/A/G   R/A/G
Traffic from West   R/A/G   R/A/G   R/A/G

—

Time: 09:00:00 + X
                    Left  Straight  Right
Traffic from North  R/A/G   R/A/G   R/A/G
Traffic from East   R/A/G   R/A/G   R/A/G
Traffic from South  R/A/G   R/A/G   R/A/G
Traffic from West   R/A/G   R/A/G   R/A/G

—

Time: 09:00:00 + X + 10
                    Left  Straight  Right
Traffic from North  R/A/G   R/A/G   R/A/G
Traffic from East   R/A/G   R/A/G   R/A/G
Traffic from South  R/A/G   R/A/G   R/A/G
Traffic from West   R/A/G   R/A/G   R/A/G

—

Time: 09:00:00 + X + 10 + Y
                    Left  Straight  Right
Traffic from North  R/A/G   R/A/G   R/A/G
Traffic from East   R/A/G   R/A/G   R/A/G
Traffic from South  R/A/G   R/A/G   R/A/G
Traffic from West   R/A/G   R/A/G   R/A/G
```

## General instructions
1. Code in Scala
2. Send a README note on how to run the program
3. Bonus point if you can describe the order-of-complexity (big-O notation) that you think your program will be able to run in (will it run in linear time? Logrithmic time? Exponential?)
4. Bonus point if you can use Akka

# Solution

## Overview
- Built with Scala and Akka
- To change base time for traffic signal i.e. change in TrafficSignalsApp.scala 
```
val BASE_SIGNAL_TIME = 6
```
- To change stand by time or Amber time for traffic signal i.e. change in TrafficState.scala 
```
val STANDBY_TIME = 2
```

## Running
Go to aworkspace directory in command prompt
```
$ git clone https://github.com/gahana/traffic-signals.git
$ cd traffic-signals
$ sbt
$ run
```
## Prerequisites
- Java 8+
- Scala 2.11
- Akka 2.4.2
- [Activator 1.3.9](http://akka.io/downloads/)

## Runtime Analysis
- Order of complexity for this app depends on what is taken as the basic counting measure. Some choices include number of roads, signals, turns possible, etc. 
- The mail logic revolves around the number of states in the TrafficState finite state machine.
- If we take the number of signals n, as the basic counting unit, the number of states is usually around 2n + 1.
- In the current scenario, for 4 signals we see 4 * 2 (amber states) + 1(standy state) = 9 states.
- This suggests the high level running time (excluding library code) is liner in the number of signals - O(n)

## Some Improvements
- Treat TrafficState as an FSM Actor
- Put traffic signal times into a config file
- Move Signal actors into TrafficState ?
