-- materials.sql
INSERT INTO warehouse_service.material (id, name) VALUES
(0, 'сталь'),
(1, 'сварочная проволока'),
(2, 'кожа'),
(3, 'пластмассы'),
(4, 'текстиль'),
(5, 'чугун');


-- materialAccountings.sql
INSERT INTO warehouse_service.material_accounting (id, material_id, quantity, type, factory_id, employer_id, date) VALUES
(0, 2, 8, 'OUTCOME', 1, 1, '2024-12-29'),
(1, 3, 12, 'OUTCOME', 1, 2, '2024-12-29'),
(2, 4, 12, 'OUTCOME', 1, 2, '2024-12-29'),
(3, 0, 2, 'OUTCOME', 2, 0, '2024-12-29'),
(4, 5, 14, 'OUTCOME', 2, 2, '2024-12-29'),
(5, 0, 6, 'OUTCOME', 0, 2, '2024-12-29'),
(6, 1, 12, 'OUTCOME', 0, 2, '2024-12-29'),
(7, 0, 1, 'OUTCOME', 2, 2, '2025-08-12'),
(8, 5, 8, 'OUTCOME', 2, 1, '2025-08-12');