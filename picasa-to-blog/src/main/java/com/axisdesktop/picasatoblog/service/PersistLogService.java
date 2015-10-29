package com.axisdesktop.picasatoblog.service;

import com.axisdesktop.picasatoblog.entity.Album;
import com.axisdesktop.picasatoblog.entity.PersistLog;
import com.axisdesktop.picasatoblog.entity.Visitor;

public interface PersistLogService {
	PersistLog savePersistLog( Visitor visitor, Album album, String ip );
}
