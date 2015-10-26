package com.axisdesktop.picasatoblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.axisdesktop.picasatoblog.entity.Visitor;
import com.axisdesktop.picasatoblog.entity.VisitorData;

public interface VisitorDataRepository extends JpaRepository<VisitorData, Long> {
	VisitorData findByVisitorIdAndIpLike( long id, String ip );

	// @Query( "SELECT COUNT(*) > 0 FROM VisitorData vd WHERE vd.visitor_id = :visitor AND vd.ip LIKE :ip" )
	// boolean existsByVisitorIdAndIpLike( @Param( "visitor" ) long visitor, @Param( "ip" ) String ip );
}
