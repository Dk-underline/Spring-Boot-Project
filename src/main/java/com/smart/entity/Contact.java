package com.smart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	private String name;
	private String nickname;
	private String number;
	@Column(unique = true)
	private String email;
	private String imageURL;
	private String place;
	@Column(length = 1000)
	private String description;
	@ManyToOne
	private User myuser;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return myuser;
	}

	public void setUser(User user) {
		this.myuser = user;
	}

	@Override
	public boolean equals(Object obj) {

		return this.cId == ((Contact) obj).getcId();
	}

//	@Override
//	public String toString() {
//		return "Contact [cId=" + cId + ", name=" + name + ", nickname=" + nickname + ", number=" + number + ", email="
//				+ email + ", imageURL=" + imageURL + ", place=" + place + ", desc=" + description + ", user=" + myuser
//				+ "]";
//	}
	@Override
	public String toString() {
		return "[cId=" + cId + ", name=" + name + ", nickname=" + nickname + ", number=" + number + ", email=" + email
				+ ", imageURL=" + imageURL + ", place=" + place + "]";
	}
}
