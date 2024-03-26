
CREATE TABLE User (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);


CREATE TABLE Message (
  message_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  chat_id INT NOT NULL,
  message_content TEXT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES User(user_id),
  FOREIGN KEY (chat_id) REFERENCES Chat(chat_id)
);


CREATE TABLE Chat (
  chat_id INT AUTO_INCREMENT PRIMARY KEY,
  chat_name VARCHAR(255) NOT NULL
);


CREATE TABLE Member (
  user_id INT NOT NULL,
  chat_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES User(user_id),
  FOREIGN KEY (chat_id) REFERENCES Chat(chat_id)
);


CREATE TABLE Calculation (
  calculation_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  task TEXT NOT NULL,
  result TEXT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES User(user_id)
);



