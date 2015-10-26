package com.axisdesktop.picasatoblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axisdesktop.picasatoblog.entity.Visitor;
import com.axisdesktop.picasatoblog.entity.VisitorData;
import com.axisdesktop.picasatoblog.model.Record;
import com.axisdesktop.picasatoblog.repository.VisitorDataRepository;
import com.axisdesktop.picasatoblog.repository.VisitorRepository;

@Service
public class VisitorServiceImpl implements VisitorService {

	@Autowired
	private VisitorRepository visitorRepo;

	@Autowired
	private VisitorDataRepository visitorDataRepo;

	@Override
	@Transactional
	public Visitor saveVisitor( Record rec ) {
		Visitor visitor = visitorRepo.findByVisitorLike( rec.getVisitor() );

		if( visitor == null ) {
			visitor = new Visitor( rec.getVisitor() );
			VisitorData vd = new VisitorData( visitor, rec.getIp() );
			visitor.getData().add( vd );
			visitorRepo.save( visitor );
		}
		else {
			VisitorData vd = visitorDataRepo.findByVisitorIdAndIpLike( visitor.getId(), rec.getIp() );
			// if( visitorDataRepo.existsByVisitorIdAndIpLike( visitor.getId(), rec.getIp() ) ) {
			if( vd == null ) {
				vd = new VisitorData( visitor, rec.getIp() );
				visitorDataRepo.save( vd );
			}
		}

		return visitor;
	}
}
