drop database if exists `sip-cd`;
create database `sip-cd` default charset utf8 collate utf8_general_ci;
USE `sip-cd`;

drop table if exists cluster;
create table cluster(
	id bigint auto_increment primary key,
	name varchar(32) not null default '' comment '集群名',
	config text comment '集群访问凭证.kube/config',
	create_time bigint not null default 0 comment '创建时间',
	update_time bigint not null default 0 comment '更新时间',
	unique key (name)
)
comment '集群' engine=InnoDB;


drop table if exists helm;
create table helm(
	id bigint auto_increment primary key,
	cluster_id bigint not null default 0 comment '集群ID',
	name varchar(32) not null default '' comment '应用名',
	namespace varchar(32) not null default '' comment '命名空间',
    template longtext not null comment 'chart values',
    version varchar(255) not null default '' comment '每个应用的版本号，格式app=v1',
	create_time bigint not null default 0 comment '创建时间',
	update_time bigint not null default 0 comment '更新时间',
	unique key (cluster_id,name)
)
comment 'helm' engine=InnoDB;
