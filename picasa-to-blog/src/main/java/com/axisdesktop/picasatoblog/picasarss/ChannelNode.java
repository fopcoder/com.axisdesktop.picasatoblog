package com.axisdesktop.picasatoblog.picasarss;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "channel" )
@XmlAccessorType( XmlAccessType.FIELD )
public class ChannelNode {
	private String title;

	@XmlElement( name = "item" )
	private List<ItemNode> items;

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public List<ItemNode> getItems() {
		return items;
	}

	public void setItems( List<ItemNode> items ) {
		this.items = items;
	}
}
