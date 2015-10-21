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

	public Visitor getVisitorId() {
		return visitor;
	}

	public void setVisitorId( Visitor visitorId ) {
		this.visitor = visitorId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp( String ip ) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "VisitorDataPK [visitorId=" + visitor + ", ip=" + ip + "]";
	}

}
