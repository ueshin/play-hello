package controllers

import _root_.models._

import _root_.play._
import _root_.play.mvc._
import _root_.play.data.validation._
import _root_.play.modules.gae._

import _root_.scala.collection.JavaConversions._

import _root_.java.util.Date

object Application extends Controller with Defaults {
  
  def index(limit: Int = 20, offset: Int = 0) = {
    val greetings = currentUser match {
      case Some(currentUser) => Greeting.homeTimeline(currentUser, limit, offset)
      case None              => Greeting.publicTimeline(limit, offset)
    }
    "@index".asTemplate(greetings)
  }

  def login = {
    currentUser match {
      case Some(currentUser) => Action(Application.index())
      case None              => GAE.login("Application.index")
    }
  }

  def logout = GAE.logout("Application.index")

  def greet(@Required message: String) = {
    currentUser match {
      case Some(currentUser) => {
        if(!Validation.hasErrors) {
          val greeting = new Greeting(currentUser.id, message)
          greeting.followerIds.addAll(currentUser.id :: Follow.followers(currentUser, Int.MaxValue).map(_.follower))
          greeting.insert
          Action(Application.index())
        }
        else {
          index()
        }
      }
      case None => Action(Application.index())
    }
  }

  def followings(limit: Int = 20, offset: Int = 0) = {
    currentUser match {
      case Some(currentUser) => {
        val followings = Follow.followings(currentUser, limit, offset)
        val users = followings.flatMap(f=> User.get(f.following).map(user=>(f->user))).toMap
        "@followings".asTemplate(followings, users)
      }
      case None => Action(Application.index())
    }
  }

  def follow(@Required @Email follow: String) = {
    currentUser match {
      case Some(currentUser) if currentUser.email != follow => {
        if(!Validation.hasErrors) {
          val now = new Date
          val user = User.get(follow) getOrElse {
            val user = new User(follow, null, now)
            user.insert
            user
          }
          if(!Follow.followings(currentUser).map(_.following).contains(user.id)) {
            new Follow(currentUser.id, user.id, now).insert
          }
          Action(Application.followings())
        }
        else {
          followings()
        }
      }
      case _ => Action(Application.followings())
    }
  }
}
