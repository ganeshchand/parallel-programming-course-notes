/**
  * Created by ganeshchand on 9/18/16.
  */

/* This implementation results in a deadlock. The program never terminates
class Account(private var amount: Int = 0) {

  /**
    * Transfer money from one account to another account
    *
    * @param target account
    * @param n      amount to be transferred
    */
  def transfer(target: Account, n: Int) = {
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }
  }

}
*/

// Ordering relationship on the resources based on Unique Account ID
class UniqueID {
  private var uidCount = 0L
  private val x = new AnyRef {}

   def getUniqueID: Long = x.synchronized {
    uidCount = uidCount + 1 // increment and update
    uidCount // return
  }
}
class Account(private var amount: Int = 0) {

  private val uid = new UniqueID().getUniqueID

  private def lockAndTransfer(target: Account, n: Int) = {
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }
  }
  /**
    * Transfer money from one account to another account
    *
    * @param target account
    * @param n      amount to be transferred
    */
  def transfer(target: Account, n: Int) = {
    if(this.uid < target.uid) this.lockAndTransfer(target, n)
    else target.lockAndTransfer(this, -n)
  }

  def getAmount = this.amount

}


object SynchronizationBlockComposition extends App{

  def startThread(from: Account, to: Account, n: Int) = {
    val t = new Thread {
      override def run(): Unit = {
        for (i <- 0 until n) {
          from.transfer(to, i)
        }
      }
    }
    t.start()
    t
  }

  val a1 = new Account(500000)
  val a2 = new Account(700000)

  val thread1 = startThread(a1, a2, 150000)
  val thread2 = startThread(a2, a1, 150000)

  thread1.join()
  thread2.join()

  println(a1.getAmount)
  println(a2.getAmount)
}
