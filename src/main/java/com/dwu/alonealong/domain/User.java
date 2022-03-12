package com.dwu.alonealong.domain;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name="userinfo")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	@Id
	@Column(name="user_id")
	private String user_id;
	private String pw;
	private String nickname;
	private String name;
	private String email;
	private String phone;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birthday;
	private Date registerdate;
	private String sex;
	private String address;
	private String zip;
	private String business_num;
	@Transient
	private String state;

	public String getId() {
		return user_id;
	}

	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	@Transient
	private String y;
	@Transient
	private String m;
	@Transient
	private String d;

	public Date getBirthday() throws ParseException {
		String birth = this.y +"/"+this.m+"/"+ this.d;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		birthday = format.parse(birth);
		return birthday;
	}
	public void setBirthday(Date birthday) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String str = format.format(birthday);
		StringTokenizer itr = new StringTokenizer(str,"/");
		y = itr.nextToken();
		m = itr.nextToken();
		d = itr.nextToken();
		this.birthday = birthday;
	}
	/* JavaBeans Properties */
}
