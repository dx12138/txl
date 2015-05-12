package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import business.User;
import models.*;

public class Application extends Controller {
	
    public static void index() {
        render();
    }
    
    
    public static void test() {
    	render();
    }
    
    public static void test2() {
//    	render();
    	renderText("test2");
    }
    
    public static void home() {
    	if (null == session.get("name")) {
    		flash.error("您需要登录后才可访问该资源");
			UserC.login();
		}
    	
        render();
    }  

}

