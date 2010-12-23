package controllers

import _root_.models._

import _root_.play._
import _root_.play.mvc._
import _root_.play.data.validation._
import _root_.play.modules.gae._

import _root_.scala.collection.JavaConversions._

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
          greeting.followerIds.add(currentUser.id)
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
}
