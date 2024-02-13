create table fishinggamerank (
gameDate	TIMESTAMP not null primary key default current_timestamp,
clearTime	int not null,
movecount	int not null
);