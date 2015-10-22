CREATE TABLE `record` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `visitor` varchar(50) NOT NULL,
  `picasa_user` varchar(50) NOT NULL,
  `picasa_album` varchar(50) NOT NULL,
  `picasa_rss` varchar(255) NOT NULL,
  `alt` varchar(200) NOT NULL,
  `ip` varchar(100) NOT NULL,
  /* `ip2` varbinary(16) NOT NULL,*/
  PRIMARY KEY (`id`)
)  /*!40101 ENGINE=InnoDB DEFAULT CHARSET=utf8 */;


CREATE TABLE `album_data` (
  `id` bigint(20) unsigned NOT NULL,
  `album_id` bigint(20) unsigned NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `album_id` INDEX (`album_id`),
  CONSTRAINT `album_data_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`)
) /*!40101 ENGINE=InnoDB DEFAULT CHARSET=utf8 */;

CREATE TABLE `album_content` (
  `album_data_id` bigint(20) unsigned NOT NULL,
  `data` longtext,
  PRIMARY KEY (`album_data_id`),
  CONSTRAINT `album_content_ibfk_1` FOREIGN KEY (`album_data_id`) REFERENCES `album_data` (`id`)
) /*!40101 ENGINE=InnoDB DEFAULT CHARSET=utf8 */;



CREATE TABLE `visitor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `visitor` char(36) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `visitor` UNIQUE (`visitor`)
) /*!40101 ENGINE=InnoDB DEFAULT CHARSET=utf8 */;

CREATE TABLE `visitor_data` (
  `visitor_id` bigint(20) unsigned NOT NULL,
  `ip` char(15) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`visitor_id`,`ip`),
  CONSTRAINT `visitor_data_ibfk_1` FOREIGN KEY (`visitor_id`) REFERENCES `visitor` (`id`)
) /*!40101 ENGINE=InnoDB DEFAULT CHARSET=utf8 */;

