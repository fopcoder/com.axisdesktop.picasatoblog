package com.axisdesktop.picasatoblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.picasatoblog.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
	Album findByExternalId( String extId );
}
