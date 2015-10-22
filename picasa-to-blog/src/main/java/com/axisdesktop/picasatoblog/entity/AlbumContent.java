package com.axisdesktop.picasatoblog.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity( name = "album_content" )
public class AlbumContent {

	@MapsId
	@OneToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "album_data_id" )
	private AlbumData albumData;

	private String data;

	public AlbumContent() {
	}

	public AlbumContent( String data ) {
		this.data = data;
	}

	public AlbumContent( AlbumData albumData, String data ) {
		this.albumData = albumData;
		this.data = data;
	}

	public AlbumData getAlbumData() {
		return albumData;
	}

	public void setAlbumData( AlbumData albumData ) {
		this.albumData = albumData;
	}

	public String getData() {
		return data;
	}

	public void setData( String data ) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AlbumContent [albumData=" + albumData + ", data=" + data + "]";
	}

}
