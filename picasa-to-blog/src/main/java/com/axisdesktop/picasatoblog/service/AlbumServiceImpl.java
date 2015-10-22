package com.axisdesktop.picasatoblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axisdesktop.picasatoblog.model.Record;
import com.axisdesktop.picasatoblog.repository.AlbumRepository;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepo;

	@Override
	@Transactional
	public void processAlbumData( Record rec ) {
		// TODO Auto-generated method stub

	}

}
