/**
  * Created by ganeshchand on 9/18/16.
  */
class WithoutSynchronizedBlock {
  private var uidCount = 0L

  def getUniqeID: Long = {
    uidCount = uidCount + 1 // increment and update
    uidCount // return
  }

  def startThread = {
    val t = new Thread {
      override def run() {
        val uids = for (i <- 0 until 10) yield getUniqeID
        println(uids)
      }
    }
    t.start()
    t
  }
}

class WithSynchronizedBlock {
  private var uidCount = 0L
  private val x = new AnyRef {}

  def getUniqeID: Long = x.synchronized{
    uidCount = uidCount + 1 // increment and update
    uidCount // return
  }

  def startThread = {
    val t = new Thread {
      override def run() {
        val uids = for (i <- 0 until 10) yield getUniqeID
        println(uids)
      }
    }
    t.start()
    t
  }
}

object SynchronizedBlock extends App {
  val test = new WithoutSynchronizedBlock

  test.getUniqeID
  test.startThread // thread 1
  test.startThread // thread 2

  val test1 = new WithSynchronizedBlock
  test1.getUniqeID
  test1.startThread // thread 1
  test1.startThread // thread 2
}

/*
You will see that two threads returning unique ids do not guarantee the unique ids.
For example, a thread could first read  UID count. After that, it adds the value 1 to it.
But before the actual assignment takes place, the seconds thread does the same thing.
If both threads now execute the assignment, they will both write teh value of one back into
uioCount. As a result, when they read uidCount, they both return 1.

To prevent such scenario, java comes with a synchronized construct which helps enforce
 atomicity. Code in the synchronized block that's invoked on the same object is never
 executed by two threads at the same time. The JVM ensures this by storing something called,
 a monitor in each object.

 At most, one thread can own a monitor at any particular time.
 For example, if a thread, T0 , owns a monitor on a object x, another thread, T1, cannot acquire that
 monitor before T0 releases it.
 */
