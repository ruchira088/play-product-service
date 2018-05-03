package daos.phantom

import com.outworkers.phantom.dsl._
import play.api.inject.ApplicationLifecycle
import utils.ConfigUtils.terminate

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag
import scala.util.{Failure, Success}

trait PhantomDao
{
  self: Database[_] =>

  def init(): Future[Seq[ResultSet]]

  def onShutdown(): Unit = shutdown()
}

object PhantomDao
{
  def initialize[T <: PhantomDao](phantomDao: T, applicationLifecycle: ApplicationLifecycle)
                                  (implicit classTag: ClassTag[T], executionContext: ExecutionContext): Unit =
    phantomDao.init().onComplete {
      case Success(_) => {
        println(classTag.runtimeClass.getSimpleName + " has been successfully initialized.")
        applicationLifecycle.addStopHook {
          () => Future.successful(phantomDao.onShutdown())
        }
      }
      case Failure(throwable) => terminate(throwable)
    }
}