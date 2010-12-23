package controllers

import _root_.models._

import _root_.play._
import _root_.play.mvc._
import _root_.play.modules.gae._

trait Defaults extends Controller {

  @Before
  def check = {
    currentUser match {
      case Some(currentUser) => {
        renderArgs += "currentUser" -> currentUser
      }
      case None =>
    }
  }

  @Util
  def currentUser = {
    Option(renderArgs.get("currentUser")) match {
      case Some(user @ User) => Some(user.asInstanceOf[User])
      case _ => Option(GAE.getUser) map {
        gaeUser => User.get(gaeUser.getEmail) getOrElse {
          val user = new User(gaeUser.getEmail)
          user.insert
          user
        }
      }
    }
  }
}
