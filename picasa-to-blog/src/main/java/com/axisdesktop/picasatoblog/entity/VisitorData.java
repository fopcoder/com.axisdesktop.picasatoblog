package com.axisdesktop.picasatoblog.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity( name = "visitor_data" )
@IdClass( VisitorDataPK.class )
public class VisitorData {

	// @Id
	// @Column( name = "visitor_id", insertable = false, updatable = false )
	// @Transient
	// private long visitorId;

	@Id
	@Column( nullable = false )
	private String ip;

	@Id
	@ManyToOne
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

	// public long getVisitorId() {
	// return visitorId;
	// }
	//
	// public void setVisitorId( long visitorId ) {
	// this.visitorId = visitorId;
	// }

	@Override
	public String toString() {
		return "VisitorData [visitor=" + visitor + ", created=" + created + ", ip=" + ip + "]";
	}

}
