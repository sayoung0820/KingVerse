INSERT INTO book (title, year_published, setting, series, summary) VALUES
('It', 1986, 'Derry', 'Standalone', 'An ancient evil returns.'),
('The Dead Zone', 1979, 'Castle Rock', 'Standalone', 'Man awakens psychic.');

INSERT INTO character_entity (name, description, first_appearance, image_url) VALUES
('Pennywise (Bob Gray)', 'Eldritch clown', (SELECT book_id FROM book WHERE title='It'), NULL),
('Johnny Smith', 'Psychic after coma', (SELECT book_id FROM book WHERE title='The Dead Zone'), NULL);

INSERT INTO character_book (character_id, book_id, role)
SELECT ce.character_id, b.book_id, 'Main'
FROM character_entity ce JOIN book b ON b.title='It'
WHERE ce.name='Pennywise (Bob Gray)';

INSERT INTO role (name) VALUES ('ROLE_ADMIN') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO user_account (username, password_hash, enabled)
VALUES ('admin', '$2a$10$8W4iS6fGZq4n2mK6rC5T7uXwq9lGm0mQy5uE1qM3B0yQ1J1x0dH2O', 1)
ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_role (user_id, role_id)
SELECT ua.user_id, r.role_id FROM user_account ua JOIN role r ON r.name='ROLE_ADMIN'
WHERE ua.username='admin' ON DUPLICATE KEY UPDATE user_id=user_role.user_id;
