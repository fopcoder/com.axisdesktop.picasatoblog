package com.axisdesktop.picasatoblog.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "album" )
public class Album {

	@Id
	@GeneratedValue
	private long id;

	@Column( updatable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar created;

	@Temporal( TemporalType.TIMESTAMP )
	private Calendar modified;

	@Column( name = "external_name", nullable = false )
	private String externalName;

	@Column( name = "external_id", nullable = false )
	private String externalId;

	@Column( name = "external_user", nullable = false )
	private String externalUser;

	@Column( name = "external_rss", nullable = false )
	private String externalRss;

	private String alt = "";

	@OneToMany( mappedBy = "album", cascade = CascadeType.PERSIST )
	private List<AlbumData> albumData = new ArrayList<>();

	@PrePersist
	private void prePersist() {
		this.created = this.modified = Calendar.getInstance();
	}

	@PreUpdate
	private void preUpdate() {
		this.modified = Calendar.getInstance();
	}

	public Album() {
	}

	public Album( String externalName, String externalId, String externalUser, String externalRss, String alt ) {
		this.externalName = externalName;
		this.externalId = externalId;
		this.externalUser = externalUser;
		this.externalRss = externalRss;
		this.alt = alt;
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated( Calendar created ) {
		this.created = created;
	}

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName( String externalName ) {
		this.externalName = externalName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId( String externalId ) {
		this.externalId = externalId;
	}

	public String getExternalUser() {
		return externalUser;
	}

	public void setExternalUser( String externalUser ) {
		this.externalUser = externalUser;
	}

	public String getExternalRss() {
		return externalRss;
	}

	public void setExternalRss( String externalRss ) {
		this.externalRss = externalRss;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt( String alt ) {
		this.alt = alt;
	}

	public List<AlbumData> getAlbumData() {
		return albumData;
	}

	public void setAlbumData( List<AlbumData> albumData ) {
		this.albumData = albumData;
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", created=" + created + ", externalName=" + externalName + ", externalId="
				+ externalId + ", externalUser=" + externalUser + ", externalRss=" + externalRss + ", alt=" + alt
				+ ", albumData=" + albumData + "]";
	}

}
