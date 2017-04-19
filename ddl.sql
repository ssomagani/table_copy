file -inlinebatch drops
drop table source if exists;
drop table dest if exists;
drop table flags if exists;
drop stream loopback_stream if exists;

drops

file -inlinebatch creates
create table source (
id integer,
name varchar(32)
);

create table dest (
id integer,
name varchar(32)
);

create table flags (
flag varchar(16),
value integer
);

create stream loopback_stream 
export to target loopback (
offset integer
);
creates

load classes classes.jar;

insert into flags values ('kill_copy_tables', 0);

insert into source values (1, 'one');
insert into source values (2, 'two');
insert into source values (3, 'three');
insert into source values (4, 'four');
insert into source values (5, 'five');
insert into source values (6, 'six');
insert into source values (7, 'seen');
insert into source values (8, 'eight');
insert into source values (9, 'nine');
insert into source values (10, 'ten');
insert into source values (11, 'eleven');
insert into source values (12, 'twelve');
insert into source values (13, 'thirteen');

create procedure from class CopyTables;
