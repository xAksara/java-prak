TRUNCATE TABLE products RESTART IDENTITY CASCADE;

-- Inserting records into products table for phones
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Phone A', 599.99, 'Brand A', 'Phone', 'Description of Phone A'),
    ('Phone B', 699.99, 'Brand B', 'Phone', 'Description of Phone B'),
    ('Phone C', 499.99, 'Brand C', 'Phone', 'Description of Phone C'),
    ('Phone D', 799.99, 'Brand D', 'Phone', 'Description of Phone D'),
    ('Phone E', 899.99, 'Brand E', 'Phone', 'Description of Phone E');

-- Inserting corresponding attributes into attributes_phone table
INSERT INTO attributes_phone (product_id, screen_diagonal, processor, memory_size, ram_size, screen_type)
VALUES
    (1, 6.5, 'Processor A', 128, 6, 'LCD'),
    (2, 6.3, 'Processor B', 256, 8, 'OLED'),
    (3, 6.0, 'Processor C', 64, 4, 'AMOLED'),
    (4, 6.7, 'Processor D', 512, 12, 'Retina'),
    (5, 6.4, 'Processor E', 256, 8, 'Super AMOLED');


-- Inserting records into products table for refrigerators
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Refrigerator A', 999.99, 'Brand X', 'Refrigerator', 'Description of Refrigerator A'),
    ('Refrigerator B', 1199.99, 'Brand Y', 'Refrigerator', 'Description of Refrigerator B'),
    ('Refrigerator C', 899.99, 'Brand Z', 'Refrigerator', 'Description of Refrigerator C'),
    ('Refrigerator D', 1299.99, 'Brand W', 'Refrigerator', 'Description of Refrigerator D'),
    ('Refrigerator E', 1499.99, 'Brand V', 'Refrigerator', 'Description of Refrigerator E');

-- Inserting corresponding attributes into attributes_refrigerator table
INSERT INTO attributes_refrigerator (product_id, capacity, energy_class, door_type, number_of_compartments, has_freezer)
VALUES
    (6, 20.0, 'A++', 'French Door', 4, TRUE),
    (7, 18.5, 'A+', 'Top Mount', 3, TRUE),
    (8, 22.0, 'A+++', 'Side-by-Side', 5, TRUE),
    (9, 19.0, 'A++', 'Bottom Freezer', 4, TRUE),
    (10, 21.5, 'A++', 'Single Door', 3, TRUE);


    -- Inserting records into products table for hairdryers
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Hairdryer A', 49.99, 'Brand F', 'Hairdryer', 'Description of Hairdryer A'),
    ('Hairdryer B', 59.99, 'Brand G', 'Hairdryer', 'Description of Hairdryer B'),
    ('Hairdryer C', 39.99, 'Brand H', 'Hairdryer', 'Description of Hairdryer C'),
    ('Hairdryer D', 69.99, 'Brand I', 'Hairdryer', 'Description of Hairdryer D'),
    ('Hairdryer E', 79.99, 'Brand J', 'Hairdryer', 'Description of Hairdryer E');

-- Inserting corresponding attributes into attributes_hairdryer table
INSERT INTO attributes_hairdryer (product_id, power_output, speed_range, heat_distribution, has_timer, has_display)
VALUES
    (11, 1800, 'Low-High', 'Even Heat', TRUE, FALSE),
    (12, 2000, 'Low-Medium-High', 'Ceramic', TRUE, TRUE),
    (13, 1600, 'Medium-High', 'Ionic', FALSE, FALSE),
    (14, 2200, 'Low-Medium-High', 'Tourmaline', TRUE, FALSE),
    (15, 2400, 'Low-Medium-High', 'Titanium', TRUE, TRUE);


-- Inserting records into products table for washing machines
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Washing Machine A', 499.99, 'Brand K', 'Washing machine', 'Description of Washing Machine A'),
    ('Washing Machine B', 599.99, 'Brand L', 'Washing machine', 'Description of Washing Machine B'),
    ('Washing Machine C', 399.99, 'Brand M', 'Washing machine', 'Description of Washing Machine C'),
    ('Washing Machine D', 699.99, 'Brand N', 'Washing machine', 'Description of Washing Machine D'),
    ('Washing Machine E', 799.99, 'Brand O', 'Washing machine', 'Description of Washing Machine E');

-- Inserting corresponding attributes into attributes_washing_machine table
INSERT INTO attributes_washing_machine (product_id, load_capacity, drum_diameter, energy_class, number_of_programs, has_delay_start)
VALUES
    (16, 8, 40.0, 'A++', 12, TRUE),
    (17, 9, 45.0, 'A++', 14, TRUE),
    (18, 7, 35.0, 'A+', 10, FALSE),
    (19, 10, 50.0, 'A++', 16, TRUE),
    (20, 11, 55.0, 'A++', 18, TRUE);


-- Inserting records into products table for dishwashers
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Dishwasher A', 699.99, 'Brand P', 'Dishwasher', 'Description of Dishwasher A'),
    ('Dishwasher B', 799.99, 'Brand Q', 'Dishwasher', 'Description of Dishwasher B'),
    ('Dishwasher C', 599.99, 'Brand R', 'Dishwasher', 'Description of Dishwasher C'),
    ('Dishwasher D', 899.99, 'Brand S', 'Dishwasher', 'Description of Dishwasher D'),
    ('Dishwasher E', 999.99, 'Brand T', 'Dishwasher', 'Description of Dishwasher E');

-- Inserting corresponding attributes into attributes_dishwasher table
INSERT INTO attributes_dishwasher (product_id, capacity, energy_class, number_of_programs, has_delay_start, has_display)
VALUES
    (21, 12, 'A++', 6, TRUE, TRUE),
    (22, 14, 'A++', 8, TRUE, TRUE),
    (23, 10, 'A+', 4, FALSE, FALSE),
    (24, 16, 'A++', 10, TRUE, TRUE),
    (25, 18, 'A++', 12, TRUE, TRUE);


-- Inserting records into products table for microwave ovens
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Microwave Oven A', 129.99, 'Brand U', 'Microwave oven', 'Description of Microwave Oven A'),
    ('Microwave Oven B', 149.99, 'Brand V', 'Microwave oven', 'Description of Microwave Oven B'),
    ('Microwave Oven C', 99.99, 'Brand W', 'Microwave oven', 'Description of Microwave Oven C'),
    ('Microwave Oven D', 169.99, 'Brand X', 'Microwave oven', 'Description of Microwave Oven D'),
    ('Microwave Oven E', 199.99, 'Brand Y', 'Microwave oven', 'Description of Microwave Oven E');

-- Inserting corresponding attributes into attributes_microwave_oven table
INSERT INTO attributes_microwave_oven (product_id, power_output, cooking_time_range, number_of_programs, has_timer, has_display)
VALUES
    (26, 800, '30 sec - 5 min', 6, TRUE, FALSE),
    (27, 900, '30 sec - 6 min', 8, TRUE, TRUE),
    (28, 700, '30 sec - 4 min', 4, FALSE, FALSE),
    (29, 1000, '30 sec - 7 min', 10, TRUE, TRUE),
    (30, 1100, '30 sec - 8 min', 12, TRUE, TRUE);


-- Inserting records into products table for toasters
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Toaster A', 39.99, 'Brand Z', 'Toaster', 'Description of Toaster A'),
    ('Toaster B', 49.99, 'Brand AA', 'Toaster', 'Description of Toaster B'),
    ('Toaster C', 29.99, 'Brand BB', 'Toaster', 'Description of Toaster C'),
    ('Toaster D', 59.99, 'Brand CC', 'Toaster', 'Description of Toaster D'),
    ('Toaster E', 69.99, 'Brand DD', 'Toaster', 'Description of Toaster E');

-- Inserting corresponding attributes into attributes_toaster table
INSERT INTO attributes_toaster (product_id, power_output, number_of_slots, has_timer, has_bread_sensor, has_display)
VALUES
    (31, 800, 2, TRUE, TRUE, FALSE),
    (32, 900, 4, TRUE, TRUE, TRUE),
    (33, 700, 2, FALSE, FALSE, FALSE),
    (34, 1000, 4, TRUE, TRUE, TRUE),
    (35, 1100, 2, TRUE, TRUE, TRUE);


-- Inserting records into products table for blenders
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Blender A', 79.99, 'Brand EE', 'Blender', 'Description of Blender A'),
    ('Blender B', 89.99, 'Brand FF', 'Blender', 'Description of Blender B'),
    ('Blender C', 69.99, 'Brand GG', 'Blender', 'Description of Blender C'),
    ('Blender D', 99.99, 'Brand HH', 'Blender', 'Description of Blender D'),
    ('Blender E', 109.99, 'Brand II', 'Blender', 'Description of Blender E');

-- Inserting corresponding attributes into attributes_blender table
INSERT INTO attributes_blender (product_id, power_output, capacity, number_of_speeds, has_timer, has_display)
VALUES
    (36, 600, 1.5, 3, FALSE, FALSE),
    (37, 700, 1.8, 4, TRUE, FALSE),
    (38, 500, 1.2, 2, FALSE, FALSE),
    (39, 800, 2.0, 5, TRUE, TRUE),
    (40, 900, 2.2, 6, TRUE, TRUE);


-- Inserting records into products table for coffee makers
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Coffee Maker A', 99.99, 'Brand JJ', 'Coffee maker', 'Description of Coffee Maker A'),
    ('Coffee Maker B', 119.99, 'Brand KK', 'Coffee maker', 'Description of Coffee Maker B'),
    ('Coffee Maker C', 79.99, 'Brand LL', 'Coffee maker', 'Description of Coffee Maker C'),
    ('Coffee Maker D', 129.99, 'Brand MM', 'Coffee maker', 'Description of Coffee Maker D'),
    ('Coffee Maker E', 149.99, 'Brand NN', 'Coffee maker', 'Description of Coffee Maker E');

-- Inserting corresponding attributes into attributes_coffee_maker table
INSERT INTO attributes_coffee_maker (product_id, water_capacity, coffee_capacity, number_of_programs, has_timer, has_display)
VALUES
    (41, 1.2, 12, 4, TRUE, FALSE),
    (42, 1.5, 14, 6, TRUE, TRUE),
    (43, 1.0, 10, 3, FALSE, FALSE),
    (44, 1.8, 16, 8, TRUE, TRUE),
    (45, 2.0, 18, 10, TRUE, TRUE);


-- Inserting records into products table for irons
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Iron A', 49.99, 'Brand OO', 'Iron', 'Description of Iron A'),
    ('Iron B', 59.99, 'Brand PP', 'Iron', 'Description of Iron B'),
    ('Iron C', 39.99, 'Brand QQ', 'Iron', 'Description of Iron C'),
    ('Iron D', 69.99, 'Brand RR', 'Iron', 'Description of Iron D'),
    ('Iron E', 79.99, 'Brand SS', 'Iron', 'Description of Iron E');

-- Inserting corresponding attributes into attributes_iron table
INSERT INTO attributes_iron (product_id, power_output, number_of_settings, has_timer, has_display, has_steam_function)
VALUES
    (46, 1800, 3, FALSE, FALSE, TRUE),
    (47, 2000, 4, TRUE, FALSE, TRUE),
    (48, 1600, 2, FALSE, FALSE, FALSE),
    (49, 2200, 5, TRUE, TRUE, TRUE),
    (50, 2400, 6, TRUE, TRUE, TRUE);


-- Inserting records into products table for vacuum cleaners
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Vacuum Cleaner A', 199.99, 'Brand TT', 'Vacuum cleaner', 'Description of Vacuum Cleaner A'),
    ('Vacuum Cleaner B', 249.99, 'Brand UU', 'Vacuum cleaner', 'Description of Vacuum Cleaner B'),
    ('Vacuum Cleaner C', 179.99, 'Brand VV', 'Vacuum cleaner', 'Description of Vacuum Cleaner C'),
    ('Vacuum Cleaner D', 299.99, 'Brand WW', 'Vacuum cleaner', 'Description of Vacuum Cleaner D'),
    ('Vacuum Cleaner E', 349.99, 'Brand XX', 'Vacuum cleaner', 'Description of Vacuum Cleaner E');

-- Inserting corresponding attributes into attributes_vacuum_cleaner table
INSERT INTO attributes_vacuum_cleaner (product_id, power_output, cleaning_area, number_of_modes, has_timer, has_display)
VALUES
    (51, 1200, 150.0, 3, FALSE, FALSE),
    (52, 1400, 180.0, 4, TRUE, FALSE),
    (53, 1000, 120.0, 2, FALSE, FALSE),
    (54, 1600, 120.0, 5, TRUE, TRUE),
    (55, 1800, 130.0, 6, TRUE, TRUE);


-- Inserting records into products table for air conditioners
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Air Conditioner A', 799.99, 'Brand YY', 'Air conditioner', 'Description of Air Conditioner A'),
    ('Air Conditioner B', 899.99, 'Brand ZZ', 'Air conditioner', 'Description of Air Conditioner B'),
    ('Air Conditioner C', 699.99, 'Brand AAA', 'Air conditioner', 'Description of Air Conditioner C'),
    ('Air Conditioner D', 999.99, 'Brand BBB', 'Air conditioner', 'Description of Air Conditioner D'),
    ('Air Conditioner E', 1099.99, 'Brand CCC', 'Air conditioner', 'Description of Air Conditioner E');

-- Inserting corresponding attributes into attributes_air_conditioner table
INSERT INTO attributes_air_conditioner (product_id, cooling_capacity, energy_class, number_of_modes, has_timer, has_display)
VALUES
    (56, 12000.0, 'A++', 3, TRUE, FALSE),
    (57, 14000.0, 'A+++', 4, TRUE, TRUE),
    (58, 10000.0, 'A+', 2, FALSE, FALSE),
    (59, 16000.0, 'A++', 5, TRUE, TRUE),
    (60, 18000.0, 'A++', 6, TRUE, TRUE);


-- Inserting records into products table for heaters
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Heater A', 129.99, 'Brand DDD', 'Heater', 'Description of Heater A'),
    ('Heater B', 149.99, 'Brand EEE', 'Heater', 'Description of Heater B'),
    ('Heater C', 99.99, 'Brand FFF', 'Heater', 'Description of Heater C'),
    ('Heater D', 169.99, 'Brand GGG', 'Heater', 'Description of Heater D'),
    ('Heater E', 199.99, 'Brand HHH', 'Heater', 'Description of Heater E');

-- Inserting corresponding attributes into attributes_heater table
INSERT INTO attributes_heater (product_id, power_output, temperature_range, number_of_modes, has_timer, has_display)
VALUES
    (61, 2000, 'Low-High', 3, TRUE, FALSE),
    (62, 2200, 'Low-Medium-High', 4, TRUE, TRUE),
    (63, 1800, 'Low-High', 2, FALSE, FALSE),
    (64, 2400, 'Low-Medium-High', 5, TRUE, TRUE),
    (65, 2600, 'Low-Medium-High', 6, TRUE, TRUE);


-- Inserting records into products table for dehumidifiers
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Dehumidifier A', 199.99, 'Brand III', 'Dehumidifier', 'Description of Dehumidifier A'),
    ('Dehumidifier B', 249.99, 'Brand JJJ', 'Dehumidifier', 'Description of Dehumidifier B'),
    ('Dehumidifier C', 179.99, 'Brand KKK', 'Dehumidifier', 'Description of Dehumidifier C'),
    ('Dehumidifier D', 299.99, 'Brand LLL', 'Dehumidifier', 'Description of Dehumidifier D'),
    ('Dehumidifier E', 349.99, 'Brand MMM', 'Dehumidifier', 'Description of Dehumidifier E');

-- Inserting corresponding attributes into attributes_dehumidifier table
INSERT INTO attributes_dehumidifier (product_id, capacity, energy_class, number_of_modes, has_timer, has_display)
VALUES
    (66, 20.0, 'A++', 3, TRUE, FALSE),
    (67, 25.0, 'A+++', 4, TRUE, TRUE),
    (68, 18.0, 'A+', 2, FALSE, FALSE),
    (69, 30.0, 'A++', 5, TRUE, TRUE),
    (70, 35.0, 'A++', 6, TRUE, TRUE);


-- Inserting records into products table for humidifiers
INSERT INTO products (name, price, brand, category, description)
VALUES
    ('Humidifier A', 99.99, 'Brand NNN', 'Humidifier', 'Description of Humidifier A'),
    ('Humidifier B', 119.99, 'Brand OOO', 'Humidifier', 'Description of Humidifier B'),
    ('Humidifier C', 79.99, 'Brand PPP', 'Humidifier', 'Description of Humidifier C'),
    ('Humidifier D', 129.99, 'Brand QQQ', 'Humidifier', 'Description of Humidifier D'),
    ('Humidifier E', 149.99, 'Brand RRR', 'Humidifier', 'Description of Humidifier E');

-- Inserting corresponding attributes into attributes_humidifier table
INSERT INTO attributes_humidifier (product_id, capacity, energy_class, number_of_modes, has_timer, has_display)
VALUES
    (71, 5.0, 'A', 3, TRUE, FALSE),
    (72, 6.0, 'A+', 4, TRUE, TRUE),
    (73, 4.0, 'A', 2, FALSE, FALSE),
    (74, 7.0, 'A+', 5, TRUE, TRUE),
    (75, 8.0, 'A++', 6, TRUE, TRUE);





-- Creating regular users
INSERT INTO users (login, password, last_name, first_name, surname, birthday, phone, email, address)
VALUES
    ('user1', 'password1', 'Doe', 'John', 'Smith', '1990-01-01', '123456789', 'user1@example.com', '123 Main St'),
    ('user2', 'password2', 'Smith', 'Jane', 'Doe', '1995-05-15', '987654321', 'user2@example.com', '456 Elm St'),
    ('user3', 'password3', 'Johnson', 'Michael', 'Brown', '1985-11-30', '555555555', 'user3@example.com', '789 Oak St'),
    ('user4', 'password4', 'Williams', 'Emily', 'Jones', '1988-07-20', '444444444', 'user4@example.com', '101 Pine St'),
    ('user5', 'password5', 'Brown', 'Sarah', 'Wilson', '1992-03-25', '333333333', 'user5@example.com', '202 Maple St'),
    ('user6', 'password6', 'Jones', 'Christopher', 'Taylor', '1982-09-10', '222222222', 'user6@example.com', '303 Birch St'),
    ('user7', 'password7', 'Wilson', 'Jessica', 'Lee', '1998-12-05', '666666666', 'user7@example.com', '404 Cedar St'),
    ('user8', 'password8', 'Lee', 'David', 'Anderson', '1993-08-15', '777777777', 'user8@example.com', '505 Pineapple St'),
    ('user9', 'password9', 'Taylor', 'Lauren', 'Clark', '1980-04-18', '888888888', 'user9@example.com', '606 Coconut St');

-- Creating admin user
INSERT INTO users (login, password, last_name, first_name, surname, birthday, phone, email, address, is_admin)
VALUES
    ('admin', 'admin_password', 'Admin', 'Admin', 'Admin', '1975-01-01', '999999999', 'admin@example.com', '707 Admin St', TRUE);




-- Sample records for the orders table
INSERT INTO orders (user_id, order_date, delivery_date, status, payment_method)
VALUES
    (1, '2024-04-01', '2024-04-05', 'Processing', 'card'),
    (2, '2024-04-02', '2024-04-06', 'Processing', 'cash'),
    (3, '2024-04-03', '2024-04-07', 'Shipped', 'card'),
    (4, '2024-04-04', '2024-04-08', 'Delivered', 'card'),
    (5, '2024-04-05', '2024-04-09', 'Delivered', 'card');

-- Sample records for the orders_products table
INSERT INTO orders_products (order_id, product_id, quantity)
VALUES
    (1, 1, 2),
    (2, 3, 1),
    (2, 5, 3),
    (3, 2, 1),
    (4, 4, 2),
    (5, 1, 1),
    (5, 6, 2);

-- Sample records for the carts table
INSERT INTO carts (user_id, product_id, quantity)
VALUES
    (1, 2, 1),
    (1, 4, 3),
    (2, 5, 2),
    (3, 3, 1),
    (4, 1, 2),
    (5, 6, 1),
    (5, 2, 2);



