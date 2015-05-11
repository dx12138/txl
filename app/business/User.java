package business;

import groovy.ui.text.FindReplaceUtility;

import java.util.Date;

import play.libs.Crypto;
import play.mvc.Http.Cookie;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import models.t_users;

public class User {
	
	public Long id;
	private Long _id;
	
	public Long getId() {
		
		return _id;
	}
	public void setId(Long id) {
		t_users user = t_users.findById(id);
		
		if (null == user) { 
			this._id = -1L;
			
			return ;
		}
		
		this._id = user.id; 
		this._name = user.name;
		this._password = user.password;
		this._create_time = user.create_time;
		this._sex = user.sex;
		this._age = user.age;
	}
	
	
	public String name;
	private String _name;
	
	public String getName() {
		
		return this._name;
	}
	public void setName(String name) {
		t_users user = t_users.find("name = ?", name).first();
		
		if (null == user) {
			this._name = name;
			this._id = -1L;
			
			return ;
		}
		
		this._id=user.id;
		this._name = user.name;
		this._password = user.password;
		this._create_time = user.create_time;
		this._sex = user.sex;
		this._age = user.age;
	}
	
	
	public String password;
	private String _password;
	
	public String getPassword() {
		
		return this._password;
	}
	public void setPassword(String password) {
		this._password = password;
	}
	
	
	public Date create_time;
	private Date _create_time;
	
	public Date getCreate_time() {
		
		return this._create_time;
	}
	public void setCreate_time(Date create_time) {
		this._create_time = create_time;
	}


	public int sex;
	private int _sex;
	
	public int getSex() {
		
		return this._sex;
	}
	public void setSex(int sex) {
		this._sex = sex;
	}
	
	
	public int age;
	private int _age;
	
	public int getAge() {
		
		return this._age;
	}
	public void setAge(int age) {
		this._age = age;
	}
	
	/**
	 * 用户登录
	 * @return 返回true表示验证成功可以登录,返回false表示验证失败不能登录
	 */
	public boolean login() {
		t_users user_login = t_users.find("password = ?", this._password).first();
		
		if (null == user_login) { 
			
			return false;
		}
		else {
			Cookie cookie = new Cookie();
			cookie.name = "id";
			cookie.value = this.id + "";
			Response.current().cookies.put("id", cookie);
			
			return true;
		}
	}
	
	/**
	 * 得到用户业务对象
	 * @return 用户业务对象
	 */
	public static User current() {
		Cookie cookie = Request.current().cookies.get("id");
		
		if (null == cookie) {
			
			return null;
		}
		else {
			User user = new User();
			user.id = Long.parseLong(cookie.value);
			
			return user;
		}
	}
	
	/**
	 * 添加用户
	 * @return 返回添加用户成功后的那条数据的id
	 */
	public long add() {
		t_users user_add = new t_users();
		user_add.name = this._name;
		user_add.password = this._password;
		user_add.create_time = new Date();
		user_add.sex = this._sex;
		user_add.age = this._age;
		user_add = user_add.save();
		
		Cookie cookie = new Cookie();
		cookie.name = "id";
		cookie.value = user_add.id + "";
		
		Response.current().cookies.put("id", cookie);
		
		return user_add.id;
	}
	
	/**
	 * 得到实体对象
	 * @param id 实体对象id
	 * @return 实体对象
	 */
	public t_users getUser() {
		
		return t_users.findById(this._id);
	}
	
}
