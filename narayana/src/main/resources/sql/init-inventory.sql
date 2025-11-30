DROP TABLE IF EXISTS inventory_item;

CREATE TABLE inventory_item (
    item_code VARCHAR(255) PRIMARY KEY,
    quantity INT NOT NULL
);

INSERT INTO inventory_item (item_code, quantity) VALUES
 ('ITEM_A', 100),
 ('ITEM_B', 50),
 ('ITEM_C', 0),
 ('ITEM_D', 75),
 ('ITEM_E', 200),
 ('ITEM_F', 10),
 ('ITEM_G', 0),
 ('ITEM_H', 500),
 ('ITEM_I', 5),
 ('ITEM_J', 300);
