create unique index if not exists users_email_uindex on users (email);

create index if not exists product_name_index on products (name);

create index if not exists purchases_product_index on purchases (product_id);

