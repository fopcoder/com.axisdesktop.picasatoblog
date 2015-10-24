package com.axisdesktop.picasatoblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.picasatoblog.entity.VisitorData;

public interface VisitorDataRepository extends JpaRepository<VisitorData, Long> {
	VisitorData findByVisitorIdAndIp( long id, String ip );
}
