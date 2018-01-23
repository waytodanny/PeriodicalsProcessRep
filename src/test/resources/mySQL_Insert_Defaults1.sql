
Insert into roles(id, name)
Values
('798fe811-feb1-11e7-a30f-7b1a8bfa155b','user'),
('89179577-feb1-11e7-a9e6-e35467cb8f58', 'admin');

Insert into users(id, login, pass, email, role_id)
values
('1f940bd3-f7a5-11e7-93e6-a30f6152aa28', 'ad', 'ad', 'waytodanny@gmail.com', '89179577-feb1-11e7-a9e6-e35467cb8f58'),
('34fa41df-f7a5-11e7-adbd-41982924721f', 'a', 'a', 'waytowill@gmail.com', '798fe811-feb1-11e7-a30f-7b1a8bfa155b'),
('57f2e55d-f7a5-11e7-89c9-8fc8f2dde3f7', 'b', 'b', 'waytojonny@gmail.com', '798fe811-feb1-11e7-a30f-7b1a8bfa155b');

Insert into genres(id, name)
values
('c1fae08d-feb1-11e7-8e6b-313d4bf1847c','comics'),
('cf8c14b2-feb1-11e7-ba96-313a67f5f27a','newspaper'),
('d74ec6d5-feb1-11e7-982f-25e096a99612','magazine'),
('dd8fe15f-feb1-11e7-a1da-4f1c9ba93af5','manga');

Insert into publishers(id, name)
values
('fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0','Marvel'),
('02e0cc80-feb2-11e7-9fc8-b3d2dbb83666','Dc'),
('071f393a-feb2-11e7-aecc-9f817ec5c610','Georgy Kongadze'),
('0e5eb194-feb2-11e7-87e1-a9292c8cf57a','Interfrax-Ukraine'),
('12df09a2-feb2-11e7-9338-8fd21bd343e0','ItCollab'),
('174b8b0b-feb2-11e7-aa99-354294c62297','Fashion World'),
('1c36da35-feb2-11e7-b255-a18dad24e342','Masasi Kishimoto');

Insert into periodicals(id, name, description, subscr_cost, issues_per_year, is_limited, genre_id, publisher_id)
values
('426f8ce0-feb2-11e7-bc2e-bd4c345931c8','Ultimate spider man', 'best spider-man story you have ever read', 12.50, 24, 1, 'c1fae08d-feb1-11e7-8e6b-313d4bf1847c', 'fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0'),
('46b26700-feb2-11e7-b445-f3b95d28ec2b','batman returns','best batman story you have ever read', 10.50, 24, 1, 'c1fae08d-feb1-11e7-8e6b-313d4bf1847c', '02e0cc80-feb2-11e7-9fc8-b3d2dbb83666'),

('4a6472a6-feb2-11e7-8ed5-edf1af6165f8','Facts', 'Facts for everyone who wants to know more', 4.5, 52, 1, 'cf8c14b2-feb1-11e7-ba96-313a67f5f27a', '071f393a-feb2-11e7-aecc-9f817ec5c610'),
('4dfe893a-feb2-11e7-847f-bfef7bccf2e5','Today','All about current day in Ukraine', 5.8, 52, 1, 'cf8c14b2-feb1-11e7-ba96-313a67f5f27a', '0e5eb194-feb2-11e7-87e1-a9292c8cf57a'),

('534453a8-feb2-11e7-ba83-eb3bef4f4dad','It Features', 'All about IT', 10.50, 24, 1, 'd74ec6d5-feb1-11e7-982f-25e096a99612', '12df09a2-feb2-11e7-9338-8fd21bd343e0'),
('56fc2c4e-feb2-11e7-acab-99ac752da3e2','Evan', 'Secrets of beaty and happiness', 8.30, 24, 1, 'd74ec6d5-feb1-11e7-982f-25e096a99612', '174b8b0b-feb2-11e7-aa99-354294c62297'),

('5a9dbc90-feb2-11e7-bb0e-81cdf6af9d4a','Naruto', 'True ninja story', 5, 24, 1, 'dd8fe15f-feb1-11e7-a1da-4f1c9ba93af5', '1c36da35-feb2-11e7-b255-a18dad24e342');

insert into periodical_issues(id, issue_no, name, publishing_date, periodical_id)
values
('e77c82ed-feb2-11e7-985b-a3ac4d073c34', 1, 'green goblin invasion', now(), '426f8ce0-feb2-11e7-bc2e-bd4c345931c8'),
('e77c82ee-feb2-11e7-985b-b940b84b0669', 2, 'rhino is in town', now(), '426f8ce0-feb2-11e7-bc2e-bd4c345931c8'),

('e77c82ef-feb2-11e7-985b-a9559739b619', 1, 'Batman vs Scarecrow', now(), '46b26700-feb2-11e7-b445-f3b95d28ec2b'),
('e77c82f0-feb2-11e7-985b-5bfcc7ebcf40', 2, 'Mr. Freeze adventures', now(),'46b26700-feb2-11e7-b445-f3b95d28ec2b'),

('e77c82f1-feb2-11e7-985b-5fe2c09344c9', 1, '', now(), '4a6472a6-feb2-11e7-8ed5-edf1af6165f8'),
('e77c82f2-feb2-11e7-985b-ebf1c04d9761', 2, '', now(), '4a6472a6-feb2-11e7-8ed5-edf1af6165f8'),

('e77c82f3-feb2-11e7-985b-8fd9bdf69ba6', 1, '', now(), '4dfe893a-feb2-11e7-847f-bfef7bccf2e5'),
('e77c82f4-feb2-11e7-985b-752ee14a7d82', 2, '', now(), '4dfe893a-feb2-11e7-847f-bfef7bccf2e5'),

('e77c82f5-feb2-11e7-985b-a7653026aadd', 1, 'What about Java future?', now(), '534453a8-feb2-11e7-ba83-eb3bef4f4dad'),
('e77c82f6-feb2-11e7-985b-e9aecf871e72', 2, '2018 languages you should know about', now(),'534453a8-feb2-11e7-ba83-eb3bef4f4dad'),

('312a82a2-feb3-11e7-b12b-0d26202b4302', 1, 'Johny Depp secrets', now(), '56fc2c4e-feb2-11e7-acab-99ac752da3e2'),
('312cf3a3-feb3-11e7-b12b-75b9623a0a09', 2, 'Tom Cruize marriage', now(), '56fc2c4e-feb2-11e7-acab-99ac752da3e2'),

('312cf3a4-feb3-11e7-b12b-972da9758744', 1, 'Naruto is coming home', now(), '5a9dbc90-feb2-11e7-bb0e-81cdf6af9d4a'),
('312cf3a5-feb3-11e7-b12b-17c388cab006', 2, 'I want to become Hokage!', now(), '5a9dbc90-feb2-11e7-bb0e-81cdf6af9d4a');
