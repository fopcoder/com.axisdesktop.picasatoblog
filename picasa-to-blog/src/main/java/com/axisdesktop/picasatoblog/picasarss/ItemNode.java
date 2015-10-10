package com.axisdesktop.picasatoblog.picasarss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "item" )
@XmlAccessorType( XmlAccessType.FIELD )
public class ItemNode {
	private String link;
	private GroupNode group;

	public String getLink() {
		return link;
	}

	public void setLink( String link ) {
		this.link = link;
	}

	public GroupNode getGroup() {
		return group;
	}

	public void setGroup( GroupNode group ) {
		this.group = group;
	}
}
