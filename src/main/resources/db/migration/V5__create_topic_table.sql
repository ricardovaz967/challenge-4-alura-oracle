CREATE TABLE topic (
code SERIAL NOT NULL,
title VARCHAR(500),
message VARCHAR(4000),
creation_date TIMESTAMP,
status INT,
author INT,
course INT,
PRIMARY KEY(code),
FOREIGN KEY(status) REFERENCES status(code),
FOREIGN KEY(author) REFERENCES "user"(code),
FOREIGN KEY(course) REFERENCES course(code)
);