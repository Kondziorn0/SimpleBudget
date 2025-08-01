ALTER TABLE income_category ADD COLUMN user_id BIGINT;
ALTER TABLE income_category ADD CONSTRAINT fk_income_category_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE expense_category ADD COLUMN user_id BIGINT;
ALTER TABLE expense_category ADD CONSTRAINT fk_expense_category_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE income ADD COLUMN user_id BIGINT;
ALTER TABLE income ADD CONSTRAINT fk_income_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE expense ADD COLUMN user_id BIGINT;
ALTER TABLE expense ADD CONSTRAINT fk_expense_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

CREATE INDEX idx_income_category_user_id ON income_category(user_id);
CREATE INDEX idx_expense_category_user_id ON expense_category(user_id);
CREATE INDEX idx_income_user_id ON income(user_id);
CREATE INDEX idx_expense_user_id ON expense(user_id);
