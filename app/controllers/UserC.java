package controllers;

import business.User;
import play.mvc.Controller;

public class UserC extends Controller {
	
	public static void login() {
		render();
	}
	
	public static void loginCheck() {
		String name = params.get("name");
		String password = params.get("password");
		
		validation.required(name).message("用户名不能为空");
		validation.required(password).message("密码不能为空");
		
		if (validation.hasErrors()) {
			validation.keep();
			login();
		}
		else {
			User user = new User();
			user.name = name;
			user.password = password;
			
			if (user.login()) {
				session.put("name", name);
				Application.home();
			}
			else {
				flash.error("用户名或密码错误,请重新输入");
			}
		}
	}
	
	public static void register() {
		render();
	}
	
	public static void registerCheck() {	
		String name = params.get("name");
		String password = params.get("password");
		String passwordOK = params.get("passwordOK");
		String sex = params.get("sex");
		String age = params.get("age");
		
		validation.required(name).message("用户名不能为空");
		validation.required(password).message("密码不能为空");
		validation.required(passwordOK).message("确认密码不能为空");
		validation.equals(passwordOK, password).message("密码和确认密码必须一致");
		validation.required(age).message("年龄不能为空");
		validation.min(age, 1).message("年龄超出正常范围");
		validation.max(age, 120).message("年龄超出正常范围");	
		
		
		if (validation.hasErrors()) {
			validation.keep();
			register();
		}
		else {
			User user = new User();
			user.name = name;
			user.password = password;
			user.sex = Integer.parseInt(sex);
			user.age = Integer.parseInt(age); 
			
			if (user.add() > 0) {
				session.put("name", name);
				Application.home();
			}
			else {
				flash.error("用户注册失败");
			}
		}
	}
	
	public static void logout() {
		session.clear();
		Application.index();
	}
	
}	
