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
@Table( name = "album_content" )
public class AlbumContent {

	@Id
	@Column( name = "album_data_id", insertable = false )
	private long albumDataId;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
	@JoinColumn( name = "album_data_id", referencedColumnName = "id" )
	private AlbumData albumData;

	private String data;

	@PrePersist
	private void prePersist() {
		this.albumDataId = this.albumData.getId();
	}

	public AlbumContent() {
	}

	public AlbumContent( AlbumData albumData, String data ) {
		this.albumData = albumData;
		this.data = data;
		this.albumDataId = this.albumData.getId();
	}

	public AlbumData getAlbumData() {
		return albumData;
	}

	public void setAlbumData( AlbumData albumData ) {
		this.albumData = albumData;
		this.albumDataId = this.albumData.getId();
	}

	public String getData() {
		return data;
	}

	public void setData( String data ) {
		this.data = data;
	}

	public long getAlbumDataId() {
		return albumDataId;
	}

	public void setAlbumDataId( long albumDataId ) {
		this.albumDataId = albumDataId;
	}

	@Override
	public String toString() {
		return "AlbumContent [albumDataId=" + albumDataId + ", albumData=" + albumData + ", data=" + data + "]";
	}

}
