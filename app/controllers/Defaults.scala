package controllers

import _root_.play._
import _root_.play.mvc._
import _root_.play.modules.gae._

trait Defaults extends Controller {

  @Before
  def check = {
    Option(GAE.getUser) match {
      case Some(user) => {
        renderArgs += "user" -> user
      }
      case None =>
    }
  }
}
