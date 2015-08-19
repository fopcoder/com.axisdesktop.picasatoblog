package com.axisdesktop.picasatoblog.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PicasaForm {
	@NotNull
	@Size( min = 10 )
	private String url;

	private String alt;

	@NotNull
	@Min( 640 )
	private int width = 1024;

	@NotNull
	@Min( 480 )
	private int height = 512;

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

}
