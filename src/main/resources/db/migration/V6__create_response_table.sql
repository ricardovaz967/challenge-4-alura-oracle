CREATE TABLE response (
code SERIAL NOT NULL,
message VARCHAR(4000),
topic INT,
creationdate TIMESTAMP,
author INT,
solution VARCHAR(4000),
PRIMARY KEY(code),
FOREIGN KEY(topic) REFERENCES topic(code),
FOREIGN KEY(author) REFERENCES "user"(code)
);