connect 'jdbc:derby://localhost/c:\jet-db\historyDB';

create schema jet_0002_team06;

create table jet_0002_team06.users(
	ID int not null generated always as identity constraint users_pk primary key,
	Name varchar(100) not null
);

create table jet_0002_team06.history(
	ID int not null generated always as identity constraint history_pk primary key,
	ReceiverName varchar(100) not null,
	MessageDate date not null,
	MessageTime time not null,
	MessageText varchar(1000),
	FK_Users int not null,
	constraint FK_history_users foreign key(FK_Users) references jet_0002_team06.users(ID)
	on delete cascade
);
