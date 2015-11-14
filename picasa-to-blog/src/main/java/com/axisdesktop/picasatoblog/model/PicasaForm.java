package com.axisdesktop.picasatoblog.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//TODO javadoc
public class PicasaForm {
	@NotNull
	@Size( min = 5, max = 200 )
	private String url;

	private String alt;

	@NotNull
	@Min( 200 )
	private int width = 1024;

	@NotNull
	@Min( 200 )
	private int height = 512;

	private String title;

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt( String alt ) {
		this.alt = alt;
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

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "PicasaForm [url=" + url + ", alt=" + alt + ", width=" + width + ", height=" + height + ", title="
				+ title + "]";
	}

}
