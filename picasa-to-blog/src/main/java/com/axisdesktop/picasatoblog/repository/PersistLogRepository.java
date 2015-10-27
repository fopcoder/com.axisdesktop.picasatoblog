package com.axisdesktop.picasatoblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.picasatoblog.entity.PersistLog;

public interface PersistLogRepository extends JpaRepository<PersistLog, Long> {

}
