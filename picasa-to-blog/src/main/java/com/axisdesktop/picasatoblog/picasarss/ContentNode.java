package com.axisdesktop.picasatoblog.picasarss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "content" )
@XmlAccessorType( XmlAccessType.FIELD )
public class ContentNode {
	@XmlAttribute( name = "url" )
	private String url;

	@XmlAttribute( name = "width" )
	private int width;

	@XmlAttribute( name = "height" )
	private int height;

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth( int width ) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight( int height ) {
		this.height = height;
	}
}
