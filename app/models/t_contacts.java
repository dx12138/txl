package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class t_contacts extends Model {
	
	//用户id
	@ManyToOne(cascade = CascadeType.ALL)
	public t_users user;
	
	//创建时间
	public Date create_time;
	
	//联系人
	@Required
	public String contactor_name;
	
	//手机号码
	@Required
	public String telphone;
	
	//备注
	public String mark;
	
}
