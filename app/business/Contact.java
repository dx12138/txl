package business;

import java.util.Date;
import java.util.List;

import utils.StringUtil;
import models.t_contacts;
import models.t_modify_contacts;
import models.t_users;

public class Contact {

	public Long id;
	private Long _id;
	
	public long getId() {
		
		return _id;
	}
	public void setId(Long id) {
		t_contacts contact = t_contacts.findById(id);
		
		if (null == contact) {
			this._id = -1L;
			
			return ;
		}
		
		this._id = contact.id;
		this._user = contact.user;
		this._create_time=contact.create_time;
		this._contactor_name = contact.contactor_name;
		this._telphone = contact.telphone;
		this._mark = contact.mark;
	}
	
	
	public t_users user;
	private t_users _user;
	
	public t_users getUser() {
		
		return this._user;
	}
	public void setUser(t_users user) {
		this._user = user;
	}


	public Date create_time;
	private Date _create_time;
	
	public Date getCreate_time() {
		
		return this._create_time;
	}
	public void setCreate_time(Date create_time) {
		this._create_time = create_time;
	}
	

	public String contactor_name;
	private String _contactor_name;
	
	public String getContactor_name() {
		
		return this._contactor_name;
	}
	public void setContactor_name(String contactor_name) {
		
		this._contactor_name = contactor_name;
	}
	
	
	public String telphone;
	private String _telphone;
	
	public String getTelphone() {
		
		return this._telphone;
	}
	public void setTelphone(String telphone) {
		this._telphone = telphone;
	}

		
	public String mark;
	private String _mark;
	
	public String getMark() {
		
		return this._mark;
	}
	public void setMark(String mark) {
		this._mark = mark;
	}
	
	public StringBuffer description;
	
	/**
	 * 得到最大页数
	 * @return 分页的最大页数
	 */
	public Long maxPage() {
		Long maxPage = t_contacts.count("user_id = ?", this._user);
		
		if (maxPage % 5 == 0) {
			
			return maxPage / 5;
		}
		else {
			
			return (maxPage / 5) + 1;
		}
	}
	
	/**
	 * 查询所有联系人
	 * @return 返回一个List集合,里面是所有联系人的数据
	 */
	public List<t_contacts> all(int page) {
		
		return t_contacts.find("user_id = ? order by id desc", this._user).fetch(page, 5);
	}
	
	/**
	 * 更新方法
	 * @return 若返回为true则表示数据更新成功,若返回为false则表示数据更新失败
	 */
	public boolean edit() {
		t_contacts contact = t_contacts.findById(this._id);
		String description = contactInformationIsmodify(contact);

		if (description.length() > 0) {
			t_modify_contacts t_modify_contact = new t_modify_contacts();
			
			t_modify_contact.user = this._user;
			t_modify_contact.modify_time = new Date();
			t_modify_contact.old_contactor_name = contact.contactor_name;
			t_modify_contact.old_telephone = contact.telphone;
			t_modify_contact.old_mark = contact.mark;
			t_modify_contact.new_contactor_name = this._contactor_name;
			t_modify_contact.new_telephone = this._telphone;
			t_modify_contact.new_mark = this._mark;
			t_modify_contact.description = description;
			t_modify_contact.save();
			
			contact.user = this._user;
			contact.create_time = this._create_time;
			contact.contactor_name = this._contactor_name;
			contact.telphone = this._telphone;
			contact.mark = this._mark;
			
			return contact.save() != null;
		}
		else {
			
			return false;
		}
		
	}
	
	/**
	 * 添加方法
	 * @return 返回成功添加进数据库的那条数据的id
	 */
	public long add() {
		t_contacts contact = new t_contacts();
		contact.create_time = new Date();
		contact.contactor_name = this._contactor_name;
		contact.telphone = this._telphone;
		contact.mark = this._mark;
		contact.user = this._user;
		
		contact = contact.save();
		
		return contact.id;
	}
	
	/**
	 * 得到实体对象
	 * @return 一个实体对象
	 */
	public t_contacts getContact() {
		
		return t_contacts.findById(this._id);
	}
	
	/**
	 * 删除方法
	 * @return 若删除成功则返回的值等于1,若不成功则返回的值等于0
	 */
	public int delete() {
		
		return t_contacts.delete("id = ?", this._id);	
	}
	
	/**
	 * 用于判断用户是否进行了数据修改,并描述修改的内容
	 * @param oldContact 存有旧值的t_contacts对象
	 * @return String 对用户修改的信息的描述
	 */
	public String contactInformationIsmodify(t_contacts oldContact) {
		boolean flag = false;
		
		description = new StringBuffer();
		
		if (! oldContact.contactor_name.equals(this._contactor_name)) {
			flag = true;
			description.append("将<" + 
								oldContact.contactor_name + ">修改为<" + this._contactor_name + ">;");
		}
		
		if (! oldContact.telphone.equals(this._telphone)) {
			flag = true;
			description.append("将<" + 
								oldContact.telphone + ">修改为<" + this._telphone + ">;");
		}
		
		if (! oldContact.mark.equals(this.mark)) {
			flag = true;
			description.append("将<" + 
								oldContact.mark + ">修改为<" + this.mark + ">;");
		}
		
		return description.toString();
	}
	
}
