TRUNCATE  kanshu.`book`;
TRUNCATE  kanshu.`book_expand`;
TRUNCATE  kanshu.`pull_book`;
TRUNCATE  kanshu.`pull_chapter`;
TRUNCATE  kanshu.`pull_volume`;
TRUNCATE  kanshu.`volume`;

TRUNCATE  kanshu_chapter.`chapter0`;
TRUNCATE  kanshu_chapter.`chapter1`;
TRUNCATE  kanshu_chapter.`chapter2`;
TRUNCATE  kanshu_chapter.`chapter3`;
TRUNCATE  kanshu_chapter.`chapter4`;
TRUNCATE  kanshu_chapter.`chapter5`;
TRUNCATE  kanshu_chapter.`chapter6`;
TRUNCATE  kanshu_chapter.`chapter7`;
TRUNCATE  kanshu_chapter.`chapter8`;
TRUNCATE  kanshu_chapter.`chapter9`;


INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),1,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),2,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),3,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),4,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),5,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),6,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),7,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),8,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),9,RAND()*1500);
INSERT INTO drive_book(book_id,create_date,TYPE,score) VALUES(RAND()*1500,NOW(),10,RAND()*1500);
