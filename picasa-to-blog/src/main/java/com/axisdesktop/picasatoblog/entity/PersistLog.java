package com.axisdesktop.picasatoblog.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "persist_log" )
public class PersistLog {
	@Id
	@GeneratedValue
	private long id;

	@Temporal( TemporalType.TIMESTAMP )
	private Calendar created;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "visitor_id", referencedColumnName = "id" )
	private Visitor visitor;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "album_id", referencedColumnName = "id" )
	private Album album;

	private String ip;

	@PrePersist
	private void prePersist() {
		this.created = Calendar.getInstance();
	}

	public PersistLog() {
	}

	public PersistLog( Visitor visitor, Album album, String ip ) {
		this.visitor = visitor;
		this.album = album;
		this.ip = ip;
	}

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

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor( Visitor visitor ) {
		this.visitor = visitor;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum( Album album ) {
		this.album = album;
	}

	public String getIp() {
		return ip;
	}

	public void setIp( String ip ) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "PersistLog [id=" + id + ", created=" + created + ", visitor=" + visitor + ", album=" + album + ", ip="
				+ ip + "]";
	}

}
