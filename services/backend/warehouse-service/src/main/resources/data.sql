-- materials.sql
INSERT INTO warehouse_service.materials (name) VALUES
('сталь'),
('сварочная проволока'),
('кожа'),
('пластмассы'),
('текстиль'),
('чугун');


-- materialAccountings.sql
INSERT INTO warehouse_service.material_accounting (material_id, quantity, type, factory_id, employer_id, date) VALUES
(2, 8, 'OUTCOME', 1, 1, '2024-12-29'),
(3, 12, 'OUTCOME', 1, 2, '2024-12-29'),
(4, 12, 'OUTCOME', 1, 2, '2024-12-29'),
(0, 2, 'OUTCOME', 2, 0, '2024-12-29'),
(5, 14, 'OUTCOME', 2, 2, '2024-12-29'),
(0, 6, 'OUTCOME', 0, 2, '2024-12-29'),
(1, 12, 'OUTCOME', 0, 2, '2024-12-29'),
(0, 1, 'OUTCOME', 2, 2, '2025-08-12'),
(5, 8, 'OUTCOME', 2, 1, '2025-08-12');