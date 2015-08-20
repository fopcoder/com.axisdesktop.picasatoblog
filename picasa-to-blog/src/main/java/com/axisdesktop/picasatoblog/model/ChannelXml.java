package com.axisdesktop.picasatoblog.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "channel" )
@XmlAccessorType( XmlAccessType.FIELD )
public class ChannelXml {

	@XmlElement
	private String title;

	@XmlElement( name = "item" )
	private List<ItemXml> items;

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public List<ItemXml> getItems() {
		return items;
	}

	public void setItems( List<ItemXml> items ) {
		this.items = items;
	}

}
