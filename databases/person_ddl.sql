drop table ADDRESS if exists;
drop table PERSON if exists;
create table PERSON (
	id 				bigint primary key not null,
	firstname		clob not null,
	lastname		clob not null,
	street			clob not null,
	zip				clob not null,
	city			clob not null,
	shipping_street	clob,
	shipping_zip	clob,
	shipping_city	clob,
);