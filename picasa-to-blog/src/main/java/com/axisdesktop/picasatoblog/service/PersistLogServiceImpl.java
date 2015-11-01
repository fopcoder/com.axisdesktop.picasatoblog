package com.axisdesktop.picasatoblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axisdesktop.picasatoblog.entity.Album;
import com.axisdesktop.picasatoblog.entity.PersistLog;
import com.axisdesktop.picasatoblog.entity.Visitor;
import com.axisdesktop.picasatoblog.repository.PersistLogRepository;

@Service
public class PersistLogServiceImpl implements PersistLogService {
	@Autowired
	private PersistLogRepository logRepo;

	@Override
	@Transactional
	public PersistLog savePersistLog( Visitor visitor, Album album, String ip ) {
		return logRepo.save( new PersistLog( visitor, album, ip ) );
	}
}
