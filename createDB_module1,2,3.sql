create database HVTSchoolV2
go
create table USERS(
USER_NAME nvarchar(50) primary key,
PASS_WORD nvarchar(50) NOT NULL,
ROLE_ID int ,
CLASS_ID_OFUSER int,
[FULL_NAME] nvarchar(50),
PHONE nvarchar(20),
EMAIL nvarchar(50),
)

go
create table ROLES(
ROLE_ID int IDENTITY(1,1) primary key,
ROLE_NAME nvarchar(50) NOT NULL
)

go
create table CLASSES(
CLASS_ID int IDENTITY(1,1) primary key,
GRADE int NOT NULL,
GIFTED_CLASS nvarchar(50) NOT NULL,
CONSTRAINT UC_CLASS UNIQUE (GRADE,GIFTED_CLASS)
)
GO
create table TEACHERS(
TEACHER_ID int IDENTITY(1,1) primary key,
[FULL_NAME] nvarchar(50) NOT NULL,
PHONE nvarchar(20),
EMAIL nvarchar(50),
MAPPING_NAME nvarchar(20) NOT NULL UNIQUE
)
go
create table MAIN_TEACHER_CLASSES(
CLASS_ID int NOT NULL,
TEACHER_ID int NOT NULL,
DATE_FROM date NOT NULL,
PRIMARY KEY (CLASS_ID,DATE_FROM),
CONSTRAINT CT_teacher_date UNIQUE (TEACHER_ID,DATE_FROM)
)
go
create table [DAY](
DAY_ID int IDENTITY(1,1) primary key,
DAY_NAME nvarchar(20),
)
go
create table TIMETABLES(
TIMETABLE_ID int IDENTITY(1,1) primary key,
TEACHER_ID int NOT NULL,
CLASS_ID int NOT NULL,
SLOT int NOT NULL,
[DAY_ID] INT NOT NULL,
[SUBJECT] nvarchar(50) NOT NULL,
APPLY_FROM date NOT NULL,
CONSTRAINT CT_TIMETABLE1 UNIQUE (CLASS_ID,SLOT,[DAY_ID],APPLY_FROM),
CONSTRAINT CT_TIMETABLE2 UNIQUE (TEACHER_ID,SLOT,[DAY_ID],APPLY_FROM)
)
go

ALTER TABLE USERS ADD CONSTRAINT fk_users_role_id
FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ROLE_ID);

GO
ALTER TABLE USERS ADD CONSTRAINT fk_users_class_id_ofuser
FOREIGN KEY (CLASS_ID_OFUSER) REFERENCES CLASSES (CLASS_ID);

GO
ALTER TABLE MAIN_TEACHER_CLASSES ADD CONSTRAINT fk_main_teacher_classes_class_id
FOREIGN KEY (CLASS_ID) REFERENCES CLASSES (CLASS_ID);

GO
ALTER TABLE MAIN_TEACHER_CLASSES ADD CONSTRAINT fk_main_teacher_classes_teacher_id
FOREIGN KEY (TEACHER_ID) REFERENCES TEACHERS (TEACHER_ID);

GO
ALTER TABLE TIMETABLES ADD CONSTRAINT fk_timetables_class_id
FOREIGN KEY (CLASS_ID) REFERENCES CLASSES (CLASS_ID);
go
ALTER TABLE TIMETABLES ADD CONSTRAINT fk_timetables_day_id
FOREIGN KEY (DAY_ID) REFERENCES [DAY](DAY_ID);
go
ALTER TABLE TIMETABLES ADD CONSTRAINT fk_timetables_teacher_id
FOREIGN KEY (TEACHER_ID) REFERENCES TEACHERS (TEACHER_ID);