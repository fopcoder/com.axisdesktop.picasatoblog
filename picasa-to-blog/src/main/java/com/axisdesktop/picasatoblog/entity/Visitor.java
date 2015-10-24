package com.axisdesktop.picasatoblog.entity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table( name = "visitor" )
public class Visitor {
	@Id
	@GeneratedValue
	private long id;

	@Column( length = 36 )
	private String visitor;

	@Column( updatable = false )
	private Calendar created;

	@OneToMany( mappedBy = "visitor", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
	Set<VisitorData> data = new HashSet<VisitorData>();

	public Visitor() {
	}

	public Visitor( String visitor ) {
		this.visitor = visitor;
	}

	@PrePersist
	private void prePersist() {
		this.created = Calendar.getInstance();
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor( String visitor ) {
		this.visitor = visitor;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated( Calendar created ) {
		this.created = created;
	}

	public Set<VisitorData> getData() {
		return data;
	}

	public void setData( Set<VisitorData> data ) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Visitor [id=" + id + ", visitor=" + visitor + ", created=" + created + ", data=" + data + "]";
	}

}
