package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class t_modify_contacts extends Model {
	
	//用户id
	@ManyToOne(cascade = CascadeType.ALL)
	public t_users user;
	
	//修改时间
	public Date modify_time;
	
	//修改前的联系人
	public String old_contactor_name;
	
	//修改前的手机号码
	public String old_telephone;
	
	//修改前的备注
	public String old_mark;
	
	//修改后的联系人
	public String new_contactor_name;
	
	//修改后的手机号码
	public String new_telephone;
	
	//修改后的备注
	public String new_mark;
	
	//描述
	public String description;
	
}
