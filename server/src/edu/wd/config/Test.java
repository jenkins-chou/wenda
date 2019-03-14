package edu.wd.config;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class Test {
	public Test(){
		
	}
		
	public static void main(String args[])
	{
		
			String url = "jdbc:mysql://localhost/mysql";
			String driver = "com.mysql.jdbc.Driver";
			try{
				Class.forName(driver);
			}catch(Exception e){
				System.out.println("无法加载驱动");
			}
			
	try {
			Connection con = (Connection) DriverManager.getConnection(url,"root","root");
			if(!con.isClosed())
				System.out.println("success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
