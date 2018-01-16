Insert into roles(name)
Values
('user'),('admin');

Insert into users(uuid, login, pass, email, roleId)
values
('1f940bd3-f7a5-11e7-93e6-a30f6152aa28', 'ad', 'ad', 'waytodanny@gmail.com', 2),
('34fa41df-f7a5-11e7-adbd-41982924721f', 'a', 'a', 'waytowill@gmail.com', 1),
('57f2e55d-f7a5-11e7-89c9-8fc8f2dde3f7', 'b', 'b', 'waytojonny@gmail.com', 1);

Insert into genres(name)
values
('comics'), ('newspaper'), ('magazine'),('manga');

Insert into publishers(name)
values
('Marvel'), ('Dc'),
('Georgy Kongadze'), ('Interfrax-Ukraine'),
('ItCollab'), ('Fashion World'),
('Masasi Kishimoto');

Insert into periodicals(name, description, subscr_cost, issues_per_year, is_limited, genre_id, publisher_id)
values
('Ultimate spider man', 'best spider-man story you have ever read', 12.50, 24, 1, 1, 1),
('batman returns','best batman story you have ever read', 10.50, 24, 1, 1, 2),

('Facts', 'Facts for everyone who wants to know more', 4.5, 52, 1, 2, 3),
('Today','All about current day in Ukraine', 5.8, 52, 1, 2, 4),

('It Features', 'All about IT', 10.50, 24, 1, 3, 5),
('Evan', 'Secrets of beaty and happiness', 8.30, 24, 1, 3, 6),

('Naruto', 'True ninja story', 5, 24, 1, 4, 7);

insert into periodical_issues(issue_no, name, publishing_date, periodicalId)
values
(1, 'green goblin invasion', now(), 1),
(2, 'rhino is in town', now(), 1),

(1, 'Batman vs Scarecrow', now(), 2),
(2, 'Mr. Freeze adventures', now(), 2),

(1, '', now(), 3),
(2, '', now(), 3),

(1, '', now(), 4),
(2, '', now(), 4),

(1, 'What about Java future?', now(), 5),
(2, '2018 languages you should know about', now(), 5),

(1, 'Johny Depp secrets', now(), 6),
(2, 'Tom Cruize marriage', now(), 6),

(1, 'Naruto is coming home', now(), 7),
(2, 'I want to become Hokage!', now(), 7);
