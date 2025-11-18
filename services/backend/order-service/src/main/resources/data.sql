-- orders.sql
INSERT INTO order_service."order" (id, product_id, product_quantity, order_date, finish_date) VALUES
(0, 4, 1, '2024-12-29', '2024-12-29'),
(1, 6, 1, '2024-12-29', '2024-12-29'),
(2, 2, 3, '2024-12-29', '2024-12-30'),
(3, 0, 1, '2025-08-12', '2025-08-12'),
(4, 6, 1, '2025-08-12', '2025-08-12');


-- ordersAccounting.sql
INSERT INTO order_service.order_accounting (id, factory_id, product_id, quantity) VALUES
(0, 1, 4, 1),
(1, 2, 6, 1),
(2, 0, 2, 3),
(3, 0, 0, 1),
(4, 2, 6, 1);