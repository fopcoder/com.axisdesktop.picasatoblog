package com.axisdesktop.picasatoblog.service;

import com.axisdesktop.picasatoblog.entity.Album;
import com.axisdesktop.picasatoblog.entity.Visitor;
import com.axisdesktop.picasatoblog.model.Record;

public interface AlbumService {
	Album saveAlbum( Visitor visitor, Record rec );
}
