-- 1. factories.sql
-- База сама выдаст им ID: 1, 2, 3
INSERT INTO factory_service.factory (name) VALUES
                                               ('Каркасный цех'),
                                               ('Цех интерьера/салона'),
                                               ('Цех запчастей');

-- 2. operations.sql
-- База выдаст им ID: 1, 2, 3, 4, 5
-- factory_id смещены (+1), чтобы указывать на правильные цеха
INSERT INTO factory_service.operation (name, factory_id, duration) VALUES
                                                                       ('изготовление каркаса', 1, 15),
                                                                       ('изготовление элементов салона', 2, 20),
                                                                       ('точение', 3, 4),
                                                                       ('Закалка стекла', 1, 0),
                                                                       ('Окраска каркаса', 1, 0);

-- 3. fctrMaterials.sql
-- factory_id смещены (+1)
INSERT INTO factory_service.factory_materials (factory_id, material_id, quantity, "createdAt") VALUES
                                                                                                   (1, 0, 1, CURRENT_DATE),
                                                                                                   (1, 1, 2, CURRENT_DATE),
                                                                                                   (2, 2, 3, CURRENT_DATE),
                                                                                                   (2, 4, 5, CURRENT_DATE),
                                                                                                   (2, 3, 5, CURRENT_DATE),
                                                                                                   (3, 0, 1, CURRENT_DATE),
                                                                                                   (3, 5, 6, CURRENT_DATE);

-- 4. opMaterials.sql
-- operation_id смещены (+1)
INSERT INTO factory_service.operation_materials (operation_id, material_id, quantity) VALUES
                                                                                          (1, 0, 1),
                                                                                          (1, 1, 2),
                                                                                          (2, 2, 4),
                                                                                          (2, 3, 6),
                                                                                          (2, 4, 6),
                                                                                          (3, 0, 1),
                                                                                          (3, 5, 7);

-- 5. opTools.sql (НОВАЯ ТАБЛИЦА)
-- Я придумал тестовые данные: привязал разные типы инструментов к твоим операциям
INSERT INTO factory_service.operation_tools (operation_id, toolType_id, quantity) VALUES
                                                                                      (1, 1, 2), -- Для изготовления каркаса (оп. 1) нужно 2 инструмента типа 1
                                                                                      (1, 4, 1), -- и 1 инструмент типа 4
                                                                                      (2, 2, 5), -- Для элементов салона (оп. 2) нужно 5 инструментов типа 2
                                                                                      (2, 5, 3),
                                                                                      (3, 3, 1), -- Для точения (оп. 3) нужен 1 инструмент типа 3
                                                                                      (4, 6, 2), -- Для закалки стекла
                                                                                      (5, 7, 4); -- Для окраски