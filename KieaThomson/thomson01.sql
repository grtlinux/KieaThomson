CREATE TABLE `tain`.`thomson` ( 
	`id`         INT                                                     NOT NULL AUTO_INCREMENT COMMENT 'id' 
	, `guid`     VARCHAR(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL                COMMENT 'guid on message' 
	, `size`     INT                                                     NOT NULL                COMMENT 'size of message' 
	, `message`  LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci     NOT NULL                COMMENT 'message' 
	, PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = 'thomson table';


select count(*) from thomson;

select message from thomson;

select * from thomson where size > 64000;

select * from thomson where size > 100000;

select message from thomson where size > 100000;


