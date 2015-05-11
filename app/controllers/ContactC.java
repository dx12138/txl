package controllers;

import java.util.List;

import models.t_contacts;
import business.Contact;
import business.User;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class ContactC extends Controller {
	
	@Before
	static void sessionCheck() {
		if (null == session.get("name")) {
			UserC.login();
		}
	}
	
	public static void all() {
		Contact contact = new Contact();
		contact.user = User.current().getUser();
		
		Long maxPage = contact.maxPage();
		int page = params.get("page", Integer.class);
		
		if(0 == page){
			page = 1;
			List<t_contacts> contact_list = contact.all(page);
			render(contact_list, maxPage, page);
		}
		else {
			List<t_contacts> contact_list = contact.all(page);
			render(contact_list, maxPage, page);
		}
	}
	
	public static void findById(String id) {
		Contact contact = new Contact();
		contact.id = Long.parseLong(id);
		
		t_contacts contacts = contact.getContact();
		render(contacts);
	}
	
	public static void delete(String id) {
		Contact contact = new Contact();
		contact.id = Long.parseLong(id);
		
		if (contact.delete() > 0) {
			redirect("/contact/all?page=1");
		}
		else {
			redirect("/contact/all?page=1");
		}
	}
	
	public static void update() {
		String contactor_name = params.get("contactor_name");
		String telphone = params.get("telphone");
		String mark = params.get("mark");
		
		validation.required(contactor_name).message("联系人姓名不能为空");
		validation.required(telphone).message("手机号码不能为空");
		validation.minSize(telphone, 11).message("手机号码需为11位");
		validation.maxSize(telphone, 11).message("手机号码需为11位");
		
		if (validation.hasErrors()) {
			validation.keep();
			findById(params.get("id"));
		}
		else {
			Contact contact = new Contact();
			contact.id = params.get("id", Long.class);
			contact.contactor_name = contactor_name;
			contact.telphone = telphone;
			contact.mark = mark;
			if (contact.edit()) {
				redirect("/contact/all?page=1");
			}
			else {
				flash.error("联系人信息未改变,无需提交");
				findById(params.get("id"));
			}
		}
	}
	
	public static void add() {
		render();
	}
	
	public static void addCheck() {
		String contactor_name = params.get("contactor_name");
		String telphone = params.get("telphone");
		String mark = params.get("mark");
		
		validation.required(contactor_name).message("联系人姓名不能为空");
		validation.required(telphone).message("手机号码不能为空");
		
		if (validation.hasErrors()) {
			validation.keep();
			add();
		}
		else {
			Contact contact = new Contact();
			contact.contactor_name = contactor_name;
			contact.telphone = telphone;
			contact.mark = mark;
			contact.user = User.current().getUser();
			
			if (contact.add() > 0) {
				redirect("/contact/all?page=1");
			}
			else {
				flash.error("添加联系人失败");
				add();
			}
		}
	}
	
}
