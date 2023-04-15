create table if not exists admin
(
	id varchar(32) not null comment '主键'
		primary key,
	account varchar(512) null comment '登录名',
	password varchar(512) null comment '密码',
	name varchar(512) null comment '名称',
	phone varchar(512) null comment '手机号',
	email varchar(512) null comment '邮箱',
	state varchar(8) null comment '状态',
	create_time bigint null comment '创建时间',
	update_time bigint null comment '更新时间'
);

create table if not exists api_key
(
	id varchar(32) not null
		primary key,
	api_key varchar(64) null comment 'openAI官网的key',
	state varchar(8) null comment 'key的状态',
	origin varchar(64) null comment '来源',
	admin_id varchar(32) null comment '关联管理员',
	create_time bigint null comment '创建时间',
	update_time bigint null comment '更新时间',
	constraint api_key_admin_id
		foreign key (admin_id) references admin (id)
);

create table if not exists user_type
(
	id varchar(32) not null comment '唯一值'
		primary key,
	code varchar(32) null comment '权限编号',
	name varchar(16) null comment '权限名称'
)
comment '角色';

create table if not exists user
(
	id varchar(32) not null comment '唯一值'
		primary key,
	account varchar(64) null comment '登录名',
	password varchar(128) null comment '密码',
	name varchar(64) null comment '真实姓名',
	phone varchar(128) null comment '手机号',
	email varchar(128) null comment '邮箱',
	state varchar(8) null comment '用户状态',
	user_type_id varchar(32) null comment '外键关联用户身份表',
	create_time bigint null comment '创建时间',
	update_time bigint null comment '更新时间',
	constraint user_user_type_id
		foreign key (user_type_id) references user_type (id)
);

create table if not exists flow
(
	id varchar(32) not null comment '主键'
		primary key,
	total int null comment '接口访问总数',
	used int null comment '已用访问次数',
	unused int null comment '剩余openai接口的访问次数',
	user_id varchar(32) null comment '外键,关联用户',
	create_time bigint null comment '创建时间',
	update_time bigint null comment '数据更新时间',
	constraint flow_user_id
		foreign key (user_id) references user (id)
)
comment '接口流量统计表';

create table if not exists user_jwt
(
	id varchar(32) not null comment '唯一值,主键'
		primary key,
	user_id varchar(32) null comment '关联用户',
	jwt varchar(512) null comment 'jwt内容',
	create_time bigint null comment '创建时间',
	expires_time bigint null comment '到期时间',
	update_time bigint null comment '修改时间',
	constraint user_jwt_user_id
		foreign key (user_id) references user (id)
);

