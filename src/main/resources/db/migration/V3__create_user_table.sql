CREATE TABLE "user" (
code SERIAL NOT NULL,
username VARCHAR(500),
email VARCHAR(500),
password VARCHAR(4000),
type_profile INT,
PRIMARY KEY(code), FOREIGN KEY(type_profile) REFERENCES profile(code)
);