package com.axisdesktop.picasatoblog.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "rss" )
public class RssXml {
	// rss -> channel -> item[]
	List<ItemXml> items = new ArrayList<>();

}
