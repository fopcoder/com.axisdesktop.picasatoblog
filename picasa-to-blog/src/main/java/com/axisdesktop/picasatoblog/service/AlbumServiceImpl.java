package com.axisdesktop.picasatoblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axisdesktop.picasatoblog.entity.Album;
import com.axisdesktop.picasatoblog.entity.Visitor;
import com.axisdesktop.picasatoblog.model.Record;
import com.axisdesktop.picasatoblog.repository.AlbumDataRepository;
import com.axisdesktop.picasatoblog.repository.AlbumRepository;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepo;

	@Autowired
	private AlbumDataRepository albumDataRepo;

	@Override
	@Transactional
	public Album saveAlbum( Visitor visitor, Record rec ) {
		Album album = albumRepo.findByVisitorAndExternalIdLike( visitor, rec.getExternalId() );

		if( album == null ) {
			album = new Album( visitor, rec.getExternalName(), rec.getExternalId(), rec.getExternalUser(),
					rec.getExternalRss(), rec.getAlt() );
			// AlbumData albumData = new AlbumData( album, rec.getExternalName() );
			// album.setAlbumData( albumData );

			albumRepo.save( album );
		}
		else if( !album.getExternalName().equals( rec.getExternalName() ) ) {
			album.setExternalName( rec.getExternalName() );
			albumRepo.save( album );
		}

		return album;
	}

}
