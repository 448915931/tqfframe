-- auto Generated on 2019-04-15 10:51:51 
-- DROP TABLE IF EXISTS `shop_entity`; 
CREATE TABLE `shop_entity`(
    `id` INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `shopname` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'shopname',
    `shopprice` INT (11) NOT NULL DEFAULT -1 COMMENT 'shopprice',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`shop_entity`';
