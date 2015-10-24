package com.axisdesktop.picasatoblog.entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table( name = "visitor_data" )
@IdClass( VisitorDataPK.class )
public class VisitorData {

	@Id
	@Column( nullable = false, length = 15 )
	private String ip;

	@Id
	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
	@JoinColumn( name = "visitor_id", referencedColumnName = "id" )
	private Visitor visitor;

	@Column( updatable = false )
	private Calendar created;

	@PrePersist
	private void prePersist() {
		this.created = Calendar.getInstance();
	}

	public VisitorData() {
	}

	public VisitorData( String ip ) {
		this.ip = ip;
	}

	public VisitorData( Visitor visitor, String ip ) {
		this.visitor = visitor;
		this.ip = ip;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor( Visitor visitor ) {
		this.visitor = visitor;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated( Calendar created ) {
		this.created = created;
	}

	public String getIp() {
		return ip;
	}

	public void setIp( String ip ) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "VisitorData [ip=" + ip + ", visitor=" + visitor + ", created=" + created + "]";
	}

}
