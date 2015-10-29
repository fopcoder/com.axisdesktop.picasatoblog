package com.axisdesktop.picasatoblog.entity;

import java.io.Serializable;

@SuppressWarnings( "serial" )
public class VisitorDataPK implements Serializable {
	private Visitor visitor;
	private String ip;

	public VisitorDataPK() {
	}

	public VisitorDataPK( Visitor visitor, String ip ) {
		this.visitor = visitor;
		this.ip = ip;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor( Visitor visitor ) {
		this.visitor = visitor;
	}

	public String getIp() {
		return ip;
	}

	public void setIp( String ip ) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "VisitorDataPK [visitor=" + visitor + ", ip=" + ip + "]";
	}

	@Override
	public int hashCode() {
		return String.format( "%d%s", this.visitor.getId(), this.ip ).hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		if( obj == null ) return false;
		if( obj == this ) return true;
		if( !( obj instanceof VisitorDataPK ) ) return false;

		VisitorDataPK pk = (VisitorDataPK)obj;

		return pk.getIp().equals( this.ip ) && pk.getVisitor().getId() == this.visitor.getId();
	}
}
