-- USER 테이블에 데이터 삽입
INSERT INTO USER (name, email, password)
VALUES ('jonghak', 'whdgkr9070@naver.com',
        '$40801$WBtCp7bIGBu7cUTGQfkthhXpraZqw7s7YiD7BP78h9Z7O7AF6q9tMootO8WBM1Ti8/02mcFFuDhMGmAONoC3rA==$esMRIoEZZ2MpsOeA2xgGqLVSdy9ypCjoYIpQQtSSvCI=');

-- TODO 테이블에 데이터 삽입
INSERT INTO TODO (user_id, title, content, reg_date)
VALUES (1, '11111111111', '111111111111111111', CURRENT_TIMESTAMP);

INSERT INTO TODO (user_id, title, content, reg_date)
VALUES (1, '2222222222222', '222222222222222', CURRENT_TIMESTAMP);

INSERT INTO TODO (user_id, title, content, reg_date)
VALUES (1, '333333333333333333', '3333333333333333', CURRENT_TIMESTAMP);

INSERT INTO TODO (user_id, title, content, reg_date)
VALUES (1, '444444444444444', '44444444444444444', CURRENT_TIMESTAMP);

INSERT INTO TODO (user_id, title, content, reg_date)
VALUES (1, '555555555555555555', '555555555555555555', CURRENT_TIMESTAMP);

INSERT INTO TODO (user_id, title, content, reg_date)
VALUES (1, '6666666666666666666', '6666666666666666', CURRENT_TIMESTAMP);

INSERT INTO TODO (user_id, title, content, reg_date)
VALUES (1, '77777777777777777', '77777777777777777', CURRENT_TIMESTAMP);