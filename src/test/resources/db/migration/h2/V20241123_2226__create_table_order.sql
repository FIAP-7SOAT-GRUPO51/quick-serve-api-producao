CREATE TABLE order_app
(
    id UUID DEFAULT RANDOM_UUID() UNIQUE,
    order_id BIGINT AUTO_INCREMENT NOT NULL,
    status VARCHAR(13) NOT NULL,
    CONSTRAINT pk_order_id PRIMARY KEY (id)
);