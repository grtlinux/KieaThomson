drop table tain.reuters;

CREATE TABLE `tain`.`reuters` ( 
	`id`               INT                                                      NOT NULL AUTO_INCREMENT COMMENT 'id on msg' 
	, `language`       VARCHAR(8)                                               NOT NULL                COMMENT 'language on msg' 
	, `activDate`      VARCHAR(16)                                              NOT NULL                COMMENT 'activeDate of msg' 
	, `timactMs`       VARCHAR(16)                                              NOT NULL                COMMENT 'activeTime of msg' 
	, `altId`          VARCHAR(32)   CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL                COMMENT 'altId of msg' 
	, `guid`           VARCHAR(128)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL                COMMENT 'guid on msg' 
	, `mimeType`       VARCHAR(128)                                             NOT NULL                COMMENT 'mimeType of msg' 
	, `headline`       VARCHAR(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL                COMMENT 'headline of msg' 
	, `body`           LONGTEXT      CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL                COMMENT 'body of msg'
	, `zipSize`        INT                                                      NOT NULL                COMMENT 'zipSize of msg' 
	, `udpSize`        INT                                                      NOT NULL                COMMENT 'udpSize of msg' 
	, `firstCreated`   VARCHAR(64)                                              NOT NULL                COMMENT 'firstCreated of msg' 
	, `versionCreated` VARCHAR(64)                                              NOT NULL                COMMENT 'versionCreated of msg' 
	, PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET=utf8 COLLATE utf8_general_ci COMMENT = 'reuters table';

select count(*) from tain.reuters;


---------------------------------------------------

select max(id) from tain.reuters;

select ifnull(max(id), 0) from tain.reuters;

select
	* 
from
	tain.thomson 
where 1=1
	and id > (
		select ifnull(max(id), 0) from tain.reuters
	)
order by
	id 
limit 10
;

---------------------------------------------------
select count(*) cnt_reuters from tain.reuters;
select count(*) cnt_thomson from tain.thomson;
---------------------------------------------------

select * from tain.reuters order by udpsize desc limit 100;

---------------------------------------------------
---------------------------------------------------
---------------------------------------------------

select count(*) from thomson;

select max(id) from thomson;


select * from thomson order by id desc limit 10;



select * from thomson where id >= 90828 and id <= 90845;

select message from thomson;

select * from thomson where size > 64000;

select * from thomson where size > 100000;

select message from thomson where size > 100000;

