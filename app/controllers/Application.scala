package controllers

import _root_.play._
import _root_.play.mvc._
import _root_.play.modules.gae._

object Application extends Controller with Defaults {
  
  def index = Template
  
  def login = GAE.login("Application.index")

  def logout = GAE.logout("Application.index")
}
