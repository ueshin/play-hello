package models

import _root_.siena._

import _root_.scala.collection.JavaConversions._

import _root_.java.util.{ ArrayList, Date }

class Follow (

  @NotNull
  var follower: Long,

  @NotNull
  var following: Long

) extends Model {

  def this() = this(0L, 0L)

  @Id
  var id: Long = _

  @NotNull
  var followedAt = new Date
}

object Follow {

  private def all = Model.all(classOf[Follow])

  def followings(user: User, limit: Int = 20, offset: Int = 0) = all.filter("follower", user.id).order("-followedAt").fetch(limit, offset).toList

  def followers(user: User, limit: Int = 20, offset: Int = 0) = all.filter("following", user.id).order("-followedAt").fetch(limit, offset).toList
}
