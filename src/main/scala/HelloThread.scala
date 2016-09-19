/**
  * Created by ganeshchand on 9/18/16.
  */

/*Steps:

 1. Create a custom thread sub-class. This sub-class will define the code that the new
 thread must execute.
 2. define run() method - code inside the run() defines what hte thread instance executes.
 2. Instantiate an object of that subclass, which will tract the execution of the thread.
 3. Call the start() on the thread object to start the actual thread execution.
 */

class HelloThread extends Thread {
  override def run(): Unit = println("Hello world!")
}
object HelloThread extends Thread{
  def main(args: Array[String]): Unit = {
    val t = new HelloThread
    t.start() // start thread
    t.join()  // wait for completion
  }
}

/*

When you run this program, the main thread starts a new thread of the HelloThread. The two
thread then execute in parallel. When the main thread calls join() it blocks its execution
until the hello thread completes.

After the HelloThread completes, the main thread can continue executing.
 */
