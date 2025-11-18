-- factories.sql
INSERT INTO factory_service.factory (id, name) VALUES
(0, 'Каркасный цех'),
(1, 'Цех интерьера/салона'),
(2, 'Цех запчастей');

-- fctrMaterials.sql
INSERT INTO factory_service.factory_materials (factory_id, material_id, quantity, "createdAt") VALUES
(0, 0, 1, CURRENT_DATE),
(0, 1, 2, CURRENT_DATE),
(1, 2, 3, CURRENT_DATE),
(1, 4, 5, CURRENT_DATE),
(1, 3, 5, CURRENT_DATE),
(2, 0, 1, CURRENT_DATE),
(2, 5, 6, CURRENT_DATE);


-- operations.sql
INSERT INTO factory_service.operation (id, name, factory_id, duration) VALUES
(0, 'изготовление каркаса', 0, 15),
(1, 'изготовление элементов салона', 1, 20),
(2, 'точение', 2, 4),
(3, 'Закалка стекла', 0, 0),
(4, 'Окраска каркаса', 0, 0);


-- opMaterials.sql
INSERT INTO factory_service.operation_materials (operation_id, material_id, quantity) VALUES
(0, 0, 1),
(0, 1, 2),
(1, 2, 4),
(1, 3, 6),
(1, 4, 6),
(2, 0, 1),
(2, 5, 7);