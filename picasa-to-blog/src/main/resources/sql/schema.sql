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