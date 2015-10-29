package com.axisdesktop.picasatoblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.picasatoblog.entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
	Visitor findByVisitorLike( String visitor );
}
