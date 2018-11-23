package example;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
	private String name;
	private String adminNum;
	
	public Student() {
	}
	
	public Student(String name, String adminNum) {
		this.name = name;
		this.adminNum = adminNum;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAdminNum() {
		return adminNum;
	}
	
	public void setAdminNum(String adminNum) {
		this.adminNum = adminNum;
	}
}
