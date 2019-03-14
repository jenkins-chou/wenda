package edu.wd.pojo;


import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class user extends Model<user> {
	public static final user te = new user();

	/**
	 * 根据用户名查找管理员
	 */
	public user findManagerByusername(String username) {
		List<user> l = null;
		l = find("select * from user where username=? ", username);
		if (l.size() > 0)
			return l.get(0);
		else
			return null;
	}

	/**
	 * 根据用户名查找管理员
	 */
	public List<user> findAllUser() {
		List<user> l = null;
		l = find("select * from user");
		if (l.size() > 0)
			return l;
		else
			return null;
	}

	

}