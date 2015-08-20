package com.axisdesktop.picasatoblog.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "item" )
@XmlAccessorType( XmlAccessType.FIELD )
public class ItemXml {
	@XmlElement
	private String link;

	@XmlElement
	private MediaXml group;

	public String getLink() {
		return link;
	}

	public void setLink( String link ) {
		this.link = link;
	}

	public MediaXml getGroup() {
		return group;
	}

	public void setGroup( MediaXml group ) {
		this.group = group;
	}

}
