package edu.wd.index;


import com.jfinal.core.Controller;

/**
 * IndexController
 */
public class IndexController extends Controller {
	public void index() {
		render("ht/login.html");
	}
	
	public void exit(){
		redirect("/");
	}
	
	public void regist(){
		render("regist.html");
	}
	
}





