-- orders.sql
INSERT INTO order_service."order" (product_id, product_quantity, order_date, finish_date) VALUES
(4, 1, '2024-12-29', '2024-12-29'),
(6, 1, '2024-12-29', '2024-12-29'),
(2, 3, '2024-12-29', '2024-12-30'),
(0, 1, '2025-08-12', '2025-08-12'),
(6, 1, '2025-08-12', '2025-08-12');


-- ordersAccounting.sql
INSERT INTO order_service.order_accounting (factory_id, product_id, quantity) VALUES
(1, 4, 1),
(2, 6, 1),
(0, 2, 3),
(0, 0, 1),
(2, 6, 1);