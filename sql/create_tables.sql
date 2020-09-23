create table contact (id bigint not null, type varchar(255), value varchar(255), person_id bigint not
null, primary key (id));
create table person (id bigint not null, age integer not null, first_name varchar(255), gender integer, last_name varchar(255), primary key (id));
alter table contact add constraint contact_person_fk foreign key (person_id) references person (id);