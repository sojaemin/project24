
package db.dto;

public class memberDTO {
private String rm;
private String no;
private String id;
private String password;
private String gubun;
private String cate;
private String email;
private String profile;
private String handphone;
private String signature;
private String regist_date;
private String last_login;
private String pro_img;
private int apply_cnt;
private int selected_cnt;
private int done_cnt;

public String getCate() {
	return cate;
}
public void setCate(String cate) {
	this.cate = cate;
}

public String getRegist_date() {
	return regist_date;
}
public void setRegist_date(String regist_date) {
	this.regist_date = regist_date;
}
public String getLast_login() {
	return last_login;
}
public void setLast_login(String last_login) {
	this.last_login = last_login;
}

public String getRm() {
	return rm;
}
public void setRm(String rm) {
	this.rm = rm;
}
public String getNo() {
	return no;
}
public void setNo(String no) {
	this.no = no;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getGubun() {
	return gubun;
}
public void setGubun(String gubun) {
	this.gubun = gubun;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getProfile() {
	return profile;
}
public void setProfile(String profile) {
	this.profile = profile;
}
public String getHandphone() {
	return handphone;
}
public void setHandphone(String handphone) {
	this.handphone = handphone;
}
public String getSignature() {
	return signature;
}
public void setSignature(String signature) {
	this.signature = signature;
}
public String getPro_img() {
	return pro_img;
}
public void setPro_img(String pro_img) {
	this.pro_img = pro_img;
}
public int getApply_cnt() {
	return apply_cnt;
}
public void setApply_cnt(int apply_cnt) {
	this.apply_cnt = apply_cnt;
}
public int getSelected_cnt() {
	return selected_cnt;
}
public void setSelected_cnt(int selected_cnt) {
	this.selected_cnt = selected_cnt;
}
public int getDone_cnt() {
	return done_cnt;
}
public void setDone_cnt(int done_cnt) {
	this.done_cnt = done_cnt;
}


	}



