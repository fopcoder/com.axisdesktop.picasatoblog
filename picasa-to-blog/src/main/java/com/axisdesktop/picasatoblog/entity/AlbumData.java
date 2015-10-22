package com.axisdesktop.picasatoblog.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity( name = "album_data" )
public class AlbumData {
	@Id
	private long id;

	private long albumId;

	@Column( updatable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar created;

	@Temporal( TemporalType.TIMESTAMP )
	private Calendar modified;

	private String title;

	@OneToOne( fetch = FetchType.LAZY )
	@PrimaryKeyJoinColumn
	private AlbumData albumData;

	@PrePersist
	private void prePersist() {
		this.created = this.modified = Calendar.getInstance();
	}

	@PreUpdate
	private void preUpdate() {
		this.modified = Calendar.getInstance();
	}

	public AlbumData() {
	}

	public AlbumData( String title ) {
		this.title = title;
	}

	public AlbumData( long albumId, String title ) {
		this.albumId = albumId;
		this.title = title;
	}

}
