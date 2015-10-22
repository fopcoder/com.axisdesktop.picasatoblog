package com.axisdesktop.picasatoblog.entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity( name = "album_data" )
public class AlbumData {
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
	@JoinColumn( name = "album_id", referencedColumnName = "id" )
	private Album album;

	@Column( updatable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar created;

	@Temporal( TemporalType.TIMESTAMP )
	private Calendar modified;

	private String title;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "albumData" )
	private AlbumContent albumContent;

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

	public AlbumData( Album album, String title ) {
		this.album = album;
		this.title = title;
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

	public Calendar getCreated() {
		return created;
	}

	public void setCreated( Calendar created ) {
		this.created = created;
	}

	public Calendar getModified() {
		return modified;
	}

	public void setModified( Calendar modified ) {
		this.modified = modified;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public AlbumContent getAlbumContent() {
		return albumContent;
	}

	public void setAlbumContent( AlbumContent albumContent ) {
		this.albumContent = albumContent;
	}

	@Override
	public String toString() {
		return "AlbumData [id=" + id + ", album=" + album + ", created=" + created + ", modified=" + modified
				+ ", title=" + title + ", albumContent=" + albumContent + "]";
	}

}
