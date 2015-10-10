package com.axisdesktop.picasatoblog.model;

import java.util.Calendar;

public class Record {
	private long id;
	private Calendar created;
	private String visitor;
	private String picasaUser;
	private String picasaAlbum;
	private String picasaRss;
	private String alt;
	private String ip;

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated( Calendar created ) {
		this.created = created;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor( String visitor ) {
		this.visitor = visitor;
	}

	public String getPicasaUser() {
		return picasaUser;
	}

	public void setPicasaUser( String picasaUser ) {
		this.picasaUser = picasaUser;
	}

	public String getPicasaAlbum() {
		return picasaAlbum;
	}

	public void setPicasaAlbum( String picasaAlbum ) {
		this.picasaAlbum = picasaAlbum;
	}

	public String getPicasaRss() {
		return picasaRss;
	}

	public void setPicasaRss( String picasaRss ) {
		this.picasaRss = picasaRss;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt( String alt ) {
		this.alt = alt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp( String ip ) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", created=" + created + ", visitor=" + visitor + ", picasaUser=" + picasaUser
				+ ", picasaAlbum=" + picasaAlbum + ", picasaRss=" + picasaRss + ", alt=" + alt + ", ip=" + ip + "]";
	}

}
