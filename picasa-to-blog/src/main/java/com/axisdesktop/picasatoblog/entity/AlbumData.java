package com.axisdesktop.picasatoblog.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table( name = "album_data" )
public class AlbumData {
	@Id
	@Column( name = "album_id" )
	private long id;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
	@JoinColumn( name = "album_id", referencedColumnName = "id" )
	private Album album;

	private String data;

	@PrePersist
	private void prePersist() {
		this.id = this.album.getId();
	}

	public AlbumData() {
	}

	public AlbumData( Album album, String data ) {
		this.album = album;
		this.id = this.album.getId();
		this.data = data;
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum( Album album ) {
		this.album = album;
	}

	public String getData() {
		return data;
	}

	public void setData( String data ) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AlbumData [id=" + id + ", album=" + album + ", data=" + data + "]";
	}

}
