package com.axisdesktop.picasatoblog.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "group" )
@XmlAccessorType( XmlAccessType.FIELD )
public class GroupNode {
	private ContentNode content;

	public ContentNode getContent() {
		return content;
	}

	public void setContent( ContentNode content ) {
		this.content = content;
	}
}
