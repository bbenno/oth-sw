INSERT INTO user_profile(id, name, bio)
VALUES (1, "Vigo Pay", "Bot to request mony from other persons.");

INSERT INTO bot(username, password, profile_id, scope, account_non_expired, account_non_locked,
                credentials_non_expired, enabled)
VALUES ("payment_service", "", 1, 4, true, true, false, true);
