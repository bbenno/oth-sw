INSERT INTO user_profile(id, `name`, bio) VALUES (100001, 'Vigo Pay Service', 'Bot to request money from other persons.');
INSERT INTO bot(username, account_non_expired, account_non_locked, credentials_non_expired, enabled, password, `scope`, profile_id) VALUES ('payment_service', TRUE, TRUE, FALSE, TRUE, '_', 3, 100001);

INSERT INTO user_profile(id, `name`, bio) VALUES (100002, 'Bank Service', 'Bank Support');
INSERT INTO bot(username, account_non_expired, account_non_locked, credentials_non_expired, enabled, password, `scope`, profile_id) VALUES ('bank_service', TRUE, TRUE, FALSE, TRUE, '_', 2, 100002);
