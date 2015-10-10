package com.axisdesktop.picasatoblog.picasarss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "rss" )
@XmlAccessorType( XmlAccessType.FIELD )
public class RssNode {
	private String version;
	private ChannelNode channel;

	public String getVersion() {
		return version;
	}

	public void setVersion( String version ) {
		this.version = version;
	}

	public ChannelNode getChannel() {
		return channel;
	}

	public void setChannel( ChannelNode channel ) {
		this.channel = channel;
	}
}
