CREATE TABLE users (
  id int auto_increment,
  email varchar(256),
  id_name varchar(256),
  name varchar(256),
  password varchar(256),
  created_at timestamp,
  updated_at timestamp,
  last_login_at timestamp
)