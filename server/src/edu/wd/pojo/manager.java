package edu.wd.pojo;



import java.util.List;

import com.jfinal.plugin.activerecord.Model;
@SuppressWarnings("serial")
public class manager extends Model<manager> {
	public static final manager te = new manager();

	/**
	 * 根据用户名查找管理员
	 */
	public manager findManagerByusername(String username) {
		List<manager> l = null;
		l = find("select * from manager where username=?", username);
		if (l.size() > 0)
			return l.get(0);
		else
			return null;
	}
}