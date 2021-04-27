package com.HackerDetector.model;

public class Datos {
	private String ip;
	private String date;
	private String action;
	private String username;
	
	public Datos(String ip, String date, String action, String username) {
		super();
		this.ip = ip;
		this.date = date;
		this.action = action;
		this.username = username;
	}
	
	public Datos() {
		
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
