package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class t_users extends Model {
	
	//用户名
	@Required
	public String name;
	
	//密码
	@Required
	public String password;
	
	//创建时间
	public Date create_time;
	
	//性别
	public int sex;
	
	//年龄
	public int age;
	
	public t_users() {
		super();
	}
	
	/**
	 * 重写Object的toString()方法,该方法用于打印出用户的个人信息
	 */
	@Override
	public String toString() {
		
		return "t_users [name=" + name + ", password=" + password + ", create_time=" 
				+ create_time + ", sex=" + sex + ", age=" + age + "]";
	}
	
}
