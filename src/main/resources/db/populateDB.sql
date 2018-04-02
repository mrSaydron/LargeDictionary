DELETE FROM learn_sets_learn_words;
DELETE FROM learn_sets;
DELETE FROM learn_books;
DELETE FROM learn_words;
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM frequency_words;
DELETE FROM prepare_words;
DELETE FROM books;
DELETE FROM translations;
DELETE FROM translation_words;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO translation_words (word, transcription, user_id) VALUES
  ('dictionary', '[ˈdɪkʃəˌnɛri]', -1),
  ('large', '[lärj]', -1),
  ('book', '[bʊk]', -1),
  ('human', '[ˈhjuːmən]', -1);

INSERT INTO translations (translation, part_of_speech, word_id) VALUES
  ('словарь', 'существительное', (SELECT id FROM translation_words WHERE word = 'dictionary')),
  ('большой', 'прилогательное', (SELECT id FROM translation_words WHERE word = 'large')),
  ('крупный', 'прилогательное', (SELECT id FROM translation_words WHERE word = 'large')),
  ('книга', 'прилогательное', (SELECT id FROM translation_words WHERE word = 'book')),
  ('человеческий', 'прилогательное', (SELECT id FROM translation_words WHERE word = 'human')),
  ('человек', 'существительное', (SELECT id FROM translation_words WHERE word = 'human'));

INSERT INTO books (title, text, amount_word, description, picture) VALUES
  ('Test book', 'xyz dictionary large book human', 5, 'Test book description', 'JAVA');

INSERT INTO prepare_words (word, start_word, end_word, book_id) VALUES
  ('xyz', 0, 2, (SELECT id FROM books WHERE title = 'Test book'));

INSERT INTO frequency_words (frequency, book_id, translation_word_id) VALUES
  (1, (SELECT id FROM books WHERE title = 'Test book'), (SELECT id FROM translation_words WHERE word = 'dictionary')),
  (1, (SELECT id FROM books WHERE title = 'Test book'), (SELECT id FROM translation_words WHERE word = 'large')),
  (1, (SELECT id FROM books WHERE title = 'Test book'), (SELECT id FROM translation_words WHERE word = 'book')),
  (1, (SELECT id FROM books WHERE title = 'Test book'), (SELECT id FROM translation_words WHERE word = 'human'));

INSERT INTO users (name, email, password) VALUES
  ('admin', 'mrsaydron@gmail.com', 'admin'),
  ('user', 'test@test.com', 'user');

INSERT INTO user_roles (user_id, role) VALUES
  ((SELECT id FROM users WHERE name = 'user'), 'ROLE_USER'),
  ((SELECT id FROM users WHERE name = 'admin'), 'ROLE_ADMIN');

INSERT INTO learn_words (translation_word_id, frequency, status, user_id) VALUES
  ((SELECT id FROM translation_words WHERE word = 'dictionary'), 1, NULL, (SELECT id FROM users WHERE name = 'user')),
  ((SELECT id FROM translation_words WHERE word = 'large'), 1, NULL, (SELECT id FROM users WHERE name = 'user')),
  ((SELECT id FROM translation_words WHERE word = 'book'), 1, NULL, (SELECT id FROM users WHERE name = 'user'));

INSERT INTO learn_books (book_id, user_id, read) VALUES
  ((SELECT id FROM books WHERE title = 'Test book'), (SELECT id FROM users WHERE name = 'user'), false);

INSERT INTO learn_sets (name, user_id) VALUES
  ('Все слова', (SELECT id FROM users WHERE name = 'user'));

INSERT INTO learn_sets_learn_words (learn_set_id, learn_word_id) VALUES
  ((SELECT id FROM learn_sets WHERE name = 'Все слова'),
     (SELECT id FROM learn_words WHERE translation_word_id = (SELECT id FROM translation_words WHERE word = 'dictionary'))),
  ((SELECT id FROM learn_sets WHERE name = 'Все слова'),
     (SELECT id FROM learn_words WHERE translation_word_id = (SELECT id FROM translation_words WHERE word = 'large'))),
  ((SELECT id FROM learn_sets WHERE name = 'Все слова'),
     (SELECT id FROM learn_words WHERE translation_word_id = (SELECT id FROM translation_words WHERE word = 'book')));

INSERT INTO translation_words (word, transcription, user_id) VALUES
  ('user', '[ˈjuːzə]', (SELECT id FROM users WHERE name = 'user'));

INSERT INTO translations (translation, part_of_speech, word_id) VALUES
  ('потребитель', 'существительное', (SELECT id FROM translation_words WHERE word = 'user'));

INSERT INTO learn_words (translation_word_id, frequency, status, user_id, user_edited) VALUES
  ((SELECT id FROM translation_words WHERE word = 'user'), 1, NULL, (SELECT id FROM users WHERE name = 'user'), true);

INSERT INTO learn_sets_learn_words (learn_set_id, learn_word_id) VALUES
  ((SELECT id FROM learn_sets WHERE name = 'Все слова'),
   (SELECT id FROM learn_words WHERE translation_word_id = (SELECT id FROM translation_words WHERE word = 'user')));