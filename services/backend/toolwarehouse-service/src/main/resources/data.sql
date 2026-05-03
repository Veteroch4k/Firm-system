-- tools.sql
INSERT INTO toolwarehouse_service.tools (tooltype_id) VALUES
                                                          (0), (1), (1), (1), (1), (1), (2), (2), (2), (2), (2),
                                                          (5), (5), (5), (5), (5), (5), (5), (5), (6), (6),
                                                          (6), (6), (6), (6), (6), (7), (7), (7), (7), (7),
                                                          (7), (7), (5), (2), (3), (6), (3), (0), (0), (0),
                                                          (4), (4), (4), (4), (4), (4), (4), (4), (4), (4),
                                                          (4), (4), (6), (6), (6), (6), (6), (6), (6), (6),
                                                          (6), (6), (6), (6), (7), (7), (7), (7), (7), (7),
                                                          (7), (7), (7), (7), (7), (7);


-- fctrTools.sql
-- (Здесь у тебя id и так не было, так что оставляем как есть)
INSERT INTO toolwarehouse_service.factory_tools (factory_id, tooltype_id, quantity) VALUES
                                                                                        (0, 0, 2),
                                                                                        (0, 4, 8),
                                                                                        (0, 6, 8),
                                                                                        (0, 7, 8),
                                                                                        (1, 1, 5),
                                                                                        (1, 2, 5),
                                                                                        (1, 5, 8),
                                                                                        (1, 6, 7),
                                                                                        (1, 7, 7),
                                                                                        (2, 3, 0),
                                                                                        (2, 6, 0);


-- freeTools.sql
INSERT INTO toolwarehouse_service.free_tools (tool_id, receive_date) VALUES
                                                                         (33, '2024-12-29'),
                                                                         (34, '2024-12-29'),
                                                                         (37, '2024-12-30'),
                                                                         (40, '2024-12-30');


-- toolAccountings.sql
INSERT INTO toolwarehouse_service.tools_accounting
(tooltype_id, quantity, factory_id, type, employee_id, date)
VALUES
    (1, 10, 1, 'INCOME', 1, '2026-05-01'),
    (2, 5,  1, 'OUTCOME', 2, '2026-05-02'),
    (5, 8,  1, 'INCOME', 1, '2026-05-02'),
    (6, 3,  1, 'OUTCOME', 3, '2026-05-03'),
    (7, 15, 1, 'INCOME', 1, '2026-05-03'),
    (3, 2,  2, 'OUTCOME', 2, '2026-05-03'),
    (6, 1,  2, 'OUTCOME', 4, '2026-05-03'),
    (0, 20, 0, 'INCOME', 1, '2026-05-03'),
    (4, 12, 0, 'INCOME', 1, '2026-05-03'),
    (6, 5,  0, 'OUTCOME', 5, '2026-05-03'),
    (7, 7,  0, 'OUTCOME', 5, '2026-05-03');