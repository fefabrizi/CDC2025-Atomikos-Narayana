DROP TABLE IF EXISTS customer_order;

CREATE TABLE customer_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_code VARCHAR(255) NOT NULL,
    requested_quantity INT NOT NULL,
    status VARCHAR(50) DEFAULT 'CREATED'
);
