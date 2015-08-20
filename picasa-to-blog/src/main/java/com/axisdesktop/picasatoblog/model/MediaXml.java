package com.axisdesktop.picasatoblog.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "group" )
@XmlAccessorType( XmlAccessType.FIELD )
public class MediaXml {
	@XmlElement( name = "content" )
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}

}
