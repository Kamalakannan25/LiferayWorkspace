create table GB_Guestbook (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	guestbookId LONG not null primary key,
	name VARCHAR(75) null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table GB_GuestbookEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	name VARCHAR(150) null,
	email VARCHAR(75) null,
	message VARCHAR(75) null,
	guestbookId LONG,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);