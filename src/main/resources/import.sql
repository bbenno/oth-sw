INSERT INTO user_profile(id, `name`, bio) VALUES (1, 'Vigo Pay', 'Bot to request money from other persons.');
INSERT INTO bot(username, account_non_expired, account_non_locked, credentials_non_expired, enabled, password, `scope`, profile_id) VALUES ('payment_service', TRUE, TRUE, FALSE, TRUE, '_', 4, 1);

