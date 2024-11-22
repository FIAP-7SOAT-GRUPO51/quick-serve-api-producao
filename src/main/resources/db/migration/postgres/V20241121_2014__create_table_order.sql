CREATE TABLE order_app
(	
	id UUID UNIQUE,
    order_id BIGSERIAL NOT NULL,
    status character varying(13) NOT NULL,
    CONSTRAINT pk_order_id PRIMARY KEY (id)
);