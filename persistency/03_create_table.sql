drop table if exists processed_blobs;
create table processed_blobs (
	hash varchar(32),
	insertTimestamp timestamp,
	CONSTRAINT table_key PRIMARY KEY(hash)
	 );
grant select, insert on public.processed_blobs to azconnect;