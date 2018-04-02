DROP TABLE IF EXISTS learn_sets_learn_words;
DROP TABLE IF EXISTS learn_sets;
DROP TABLE IF EXISTS learn_books;
DROP TABLE IF EXISTS learn_words;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS frequency_words;
DROP TABLE IF EXISTS prepare_words;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS translations;
DROP TABLE IF EXISTS translation_words;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE translation_words
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  word             VARCHAR                 NOT NULL,
  transcription    VARCHAR                 NOT NULL,
  user_id          INTEGER DEFAULT -1      NOT NULL
);
CREATE UNIQUE INDEX translation_word_unique_word_idx ON translation_words (word, user_id);

CREATE TABLE translations
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  translation      VARCHAR                 NOT NULL,
  part_of_speech   VARCHAR                 NOT NULL,
  word_id          INTEGER                 NOT NULL,
  FOREIGN KEY (word_id) REFERENCES translation_words (id) ON DELETE CASCADE
);

CREATE TABLE books
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  title            VARCHAR NOT NULL,
  author           VARCHAR,
  url              VARCHAR,
  text             TEXT                 NOT NULL,
  amount_word      INTEGER              NOT NULL,
  description      VARCHAR,
  picture          VARCHAR
);
CREATE UNIQUE INDEX book_unique_title_idx ON books (title);

CREATE TABLE prepare_words
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  word             VARCHAR              NOT NULL,
  start_word       INTEGER              NOT NULL,
  end_word         INTEGER              NOT NULL,
  book_id          INTEGER              NOT NULL,
  FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);

CREATE TABLE frequency_words
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  frequency        INTEGER              NOT NULL,
  book_id          INTEGER              NOT NULL,
  translation_word_id INTEGER           NOT NULL,
  FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
  FOREIGN KEY (translation_word_id) REFERENCES translation_words (id) ON DELETE CASCADE
);

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR              NOT NULL,
  email            VARCHAR              NOT NULL,
  password         VARCHAR              NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);
CREATE UNIQUE INDEX users_unique_name_idx ON users (name);

CREATE TABLE user_roles
(
  user_id           INTEGER             NOT NULL,
  role              VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE learn_words
(
  id                INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  translation_word_id INTEGER           NOT NULL,
  frequency        INTEGER              NOT NULL,
  create_date      TIMESTAMP DEFAULT now() NOT NULL,
  status           VARCHAR,
  user_id          INTEGER              NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (translation_word_id) REFERENCES translation_words (id) ON DELETE CASCADE,
  user_edited      BOOLEAN DEFAULT FALSE   NOT NULL
);
CREATE UNIQUE INDEX learn_words_unique_word_idx ON learn_words (user_id, translation_word_id);

CREATE TABLE learn_books
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  book_id          INTEGER              NOT NULL,
  user_id          INTEGER              NOT NULL,
  read             BOOLEAN              NOT NULL,
  FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX learn_books_unique_book_user_idx ON learn_books (user_id, book_id);

CREATE TABLE learn_sets
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR              NOT NULL,
  user_id          INTEGER              NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX learn_sets_unique_name_idx ON learn_sets (user_id, name);

CREATE TABLE learn_sets_learn_words
(
  learn_set_id     INTEGER              NOT NULL,
  learn_word_id    INTEGER              NOT NULL,
  FOREIGN KEY (learn_set_id) REFERENCES learn_sets (id) ON DELETE CASCADE,
  FOREIGN KEY (learn_word_id) REFERENCES learn_words (id) ON DELETE CASCADE
);