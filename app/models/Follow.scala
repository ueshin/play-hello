package models

import _root_.siena._

import _root_.scala.collection.JavaConversions._

import _root_.java.util.{ ArrayList, Date }

@serializable
class Follow (

  @NotNull
  var follower: Long,

  @NotNull
  var following: Long,

  @NotNull
  var followedAt: Date = new Date

) extends Model {

  def this() = this(0L, 0L)

  @Id
  var id: Long = _
}

object Follow {

  private def all = Model.all(classOf[Follow])

  def followings(user: User) = all.filter("follower", user.id).order("-followedAt").fetch(Int.MaxValue).toList

  def followers(user: User) = all.filter("following", user.id).order("-followedAt").fetch(Int.MaxValue).toList
}
