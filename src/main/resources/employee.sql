DROP TABLE EMPLOYEE IF EXISTS;

CREATE TABLE EMPLOYEE  (
    ID INT NOT NULL ,
    FIRSTNAME VARCHAR(100),
    LASTNAME VARCHAR(100),
    CONSTRAINT pk_EMPLOYEE PRIMARY KEY (ID)
) ;