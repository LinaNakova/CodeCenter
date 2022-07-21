INSERT INTO app_users(username, name, surname, email)
VALUES ('jovan', 'jovan', 'dimov', 'jovandimov@gmail.com'),
       ('test', 'test', 'test', 'test@sorsix.com');

INSERT INTO question(title, question_text, parent_question_id, app_user_id)
VALUES ('first question', 'testtest', null, 1),
       ('first answer to first question', 'answer', 1, 2);

INSERT INTO tag(name, description)
VALUES ('kotlin', 'this is ...'),
       ('typescript', 'this is better than js');

INSERT INTO question_tag(question_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);