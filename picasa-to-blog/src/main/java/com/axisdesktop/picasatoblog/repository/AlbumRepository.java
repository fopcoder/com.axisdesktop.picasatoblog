package com.axisdesktop.picasatoblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.picasatoblog.entity.Album;
import com.axisdesktop.picasatoblog.entity.Visitor;

public interface AlbumRepository extends JpaRepository<Album, Long> {
	Album findByVisitorAndExternalIdLike( Visitor visitor, String extId );
}
