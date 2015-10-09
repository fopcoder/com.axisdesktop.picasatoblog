package com.axisdesktop.picasatoblog.model;

//TODO javadoc
public class BlogImage {
	private int width;
	private int height;
	private String url;
	private String alt;

	public BlogImage() {
	}

	public BlogImage( int width, int height, String url, String alt ) {
		this.width = width;
		this.height = height;
		this.url = url;
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

}
