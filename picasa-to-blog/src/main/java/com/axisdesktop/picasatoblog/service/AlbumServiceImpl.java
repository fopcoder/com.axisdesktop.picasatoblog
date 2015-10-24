package com.axisdesktop.picasatoblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axisdesktop.picasatoblog.entity.Album;
import com.axisdesktop.picasatoblog.entity.AlbumContent;
import com.axisdesktop.picasatoblog.entity.AlbumData;
import com.axisdesktop.picasatoblog.model.Record;
import com.axisdesktop.picasatoblog.repository.AlbumContentRepository;
import com.axisdesktop.picasatoblog.repository.AlbumDataRepository;
import com.axisdesktop.picasatoblog.repository.AlbumRepository;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepo;

	@Autowired
	private AlbumDataRepository albumDataRepo;

	@Autowired
	private AlbumContentRepository albumContentRepo;

	@Override
	@Transactional
	public Album saveAlbum( Record rec ) {
		Album album = albumRepo.findByExternalId( rec.getExternalId() );

		if( album == null ) {
			album = new Album( rec.getExternalName(), rec.getExternalId(), rec.getExternalUser(), rec.getExternalRss(),
					rec.getAlt() );
			AlbumData albumData = new AlbumData( album, rec.getExternalName() );
			AlbumContent albumContent = new AlbumContent( albumData, "test content" );

			album.getAlbumData().add( albumData );
			albumData.setAlbumContent( albumContent );

			albumRepo.save( album );
		}
		else if( !album.getExternalName().equals( rec.getExternalName() ) ) {
			album.setExternalName( rec.getExternalName() );
			albumRepo.save( album );
		}

		return album;
	}
}
