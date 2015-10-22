package com.axisdesktop.picasatoblog.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity( name = "album_content" )
@SuppressWarnings( "serial" )
public class AlbumContent implements Serializable {

	// @Id
	// @Column(name="OWNER_ID")
	// private long ownerId;
	//
	// @OneToOne
	// @PrimaryKeyJoinColumn(name="OWNER_ID", referencedColumnName="EMP_ID")
	// private Employee owner;
	// ...
	//
	// public void setOwner(Employee owner) {
	// this.owner = owner;
	// this.ownerId = owner.getId();
	// }

	@Id
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
	@JoinColumn( name = "album_data_id", referencedColumnName = "id" )
	private AlbumData albumData;

	private String data;

	public AlbumContent() {
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
