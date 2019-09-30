create table comment
(
	id bigint auto_increment,
	parent_id bigint not null,
	type int,
	commentator int not null,
	gmt_create bigint not null,
	gmt_modified bigint not null,
	like_count int default 0,
	content text not null,
	constraint comment_pk
		primary key (id)
);
