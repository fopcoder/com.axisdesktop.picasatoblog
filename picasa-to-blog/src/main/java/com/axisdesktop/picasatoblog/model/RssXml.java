package com.axisdesktop.picasatoblog.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "rss" )
@XmlAccessorType( XmlAccessType.FIELD )
public class RssXml {
	// rss -> channel -> item[]

	@XmlAttribute
	private String version;

	@XmlElement( name = "channel" )
	private ChannelXml channel;

	public String getVersion() {
		return version;
	}

	public void setVersion( String version ) {
		this.version = version;
	}

	public ChannelXml getChannel() {
		return channel;
	}

	public void setChannel( ChannelXml channel ) {
		this.channel = channel;
	}

}
