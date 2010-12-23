package models

import _root_.siena._

import _root_.scala.collection.JavaConversions._

import _root_.java.util.{ ArrayList, Date }

class Greeting (

  @NotNull
  var userId: Long,

  @NotNull
  var message: String

) extends Model {

  def this() = this(0L, null)

  @Id
  var id: Long = _

  @NotNull
  var postedAt = new Date

  @NotNull
  var followerIds = new ArrayList[Long]
}

object Greeting {

  private def all = Model.all(classOf[Greeting])

  def get(id: Long) = Option(all.filter("id", id).get)

  def homeTimeline(user: User, limit: Int = 20, offset: Int = 0) = all.filter("followerIds", user.id).order("-postedAt").fetch(limit, offset).toList

  def publicTimeline(limit: Int = 20, offset: Int = 0) = all.order("-postedAt").fetch(limit, offset).toList
}
