DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE users (
                       user_id serial PRIMARY KEY,
                       login text NOT NULL UNIQUE,
                       password text NOT NULL,
                       last_name text,
                       first_name text,
                       surname text,
                       birthday date,
                       phone text,
                       email text,
                       address text,
                       is_admin bool DEFAULT FALSE
);

CREATE TABLE product_categories (
    category_id integer PRIMARY KEY,
    category text
);

INSERT INTO product_categories (category_id, category)
VALUES
    (1, 'phone'),
    (2, 'refrigerator'),
    (3, 'hairdryer'),
    (4, 'washing_machine'),
    (5, 'dishwasher'),
    (6, 'microwave_oven'),
    (7, 'toaster'),
    (8, 'blender'),
    (9, 'coffee_maker'),
    (10, 'iron'),
    (11, 'vacuum_cleaner'),
    (12, 'air_conditioner'),
    (13, 'heater'),
    (14, 'dehumidifier'),
    (15, 'humidifier');


CREATE TABLE payment_methods (
                                    method_id integer PRIMARY KEY,
                                    method text
);

INSERT INTO payment_methods (method_id, method)
VALUES
    (1, 'cash'),
    (2, 'card');

CREATE TABLE order_statuses (
                                      status_id integer PRIMARY KEY,
                                      status text
  );

INSERT INTO order_statuses (status_id, status)
VALUES
    (1, 'processing'),
    (2, 'shipped'),
    (3, 'delivered');

-- CREATE TYPE product_category_type AS ENUM (
--     'phone',
--     'refrigerator',
--     'hairdryer',
--     'washing_machine',
--     'dishwasher',
--     'microwave_oven',
--     'toaster',
--     'blender',
--     'coffee_maker',
--     'iron',
--     'vacuum_cleaner',
--     'air_conditioner',
--     'heater',
--     'dehumidifier',
--     'humidifier'
--     );
--
--
--
-- CREATE TYPE order_status_type AS ENUM ('Processing', 'Shipped', 'Delivered');
-- CREATE TYPE order_payment_type AS ENUM ('cash', 'card');

CREATE TABLE products (
                          product_id serial PRIMARY KEY,
                          name text NOT NULL,
                          price integer,
                          brand text,
                          category_id integer,
                          description text,
                          quantity int NOT NULL DEFAULT 0,
                          bought int NOT NULL DEFAULT 0,
                          img_path text NOT NULL DEFAULT 'path/to/placeholder',
                          FOREIGN KEY (category_id) REFERENCES product_categories(category_id)
);

CREATE TABLE orders (
                        order_id serial PRIMARY KEY,
                        user_id  int,
                        order_date date,
                        delivery_date date,
                        status_id integer,
                        payment_method_id integer,
                        FOREIGN KEY (user_id) REFERENCES users(user_id),
                        FOREIGN KEY (status_id) REFERENCES order_statuses(status_id),
                        FOREIGN KEY (payment_method_id) REFERENCES payment_methods(method_id)
);

CREATE TABLE orders_products (
                        order_id int,
                        product_id  int,
						quantity int,
						PRIMARY KEY (order_id, product_id),
						FOREIGN KEY (order_id) REFERENCES orders(order_id),
						FOREIGN KEY (product_id) REFERENCES products(product_id)
);


CREATE TABLE carts (
                        user_id int,
                        product_id  int,
						quantity int,
						PRIMARY KEY (user_id, product_id),
						FOREIGN KEY (product_id) REFERENCES products(product_id),
						FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE attributes_phone (
                                  product_id INTEGER UNIQUE REFERENCES products(product_id),
                                  screen_diagonal DECIMAL(8,2),
                                  processor VARCHAR(255),
                                  memory_size INTEGER,
                                  ram_size INTEGER,
                                  screen_type VARCHAR(255)
);

CREATE TABLE attributes_refrigerator (
                                         product_id INTEGER UNIQUE REFERENCES products(product_id),
                                         capacity DECIMAL(8,2),
                                         energy_class VARCHAR(255),
                                         door_type VARCHAR(255),
                                         number_of_compartments INTEGER,
                                         has_freezer BOOLEAN
);

CREATE TABLE attributes_hairdryer (
                                      product_id INTEGER UNIQUE REFERENCES products(product_id),
                                      power_output INTEGER,
                                      speed_range VARCHAR(255),
                                      heat_distribution VARCHAR(255),
                                      has_timer BOOLEAN,
                                      has_display BOOLEAN
);

CREATE TABLE attributes_washing_machine (
                                            product_id INTEGER UNIQUE REFERENCES products(product_id),
                                            load_capacity INTEGER,
                                            drum_diameter DECIMAL(8,2),
                                            energy_class VARCHAR(255),
                                            number_of_programs INTEGER,
                                            has_delay_start BOOLEAN
);

CREATE TABLE attributes_dishwasher (
                                       product_id INTEGER UNIQUE REFERENCES products(product_id),
                                       capacity INTEGER,
                                       energy_class VARCHAR(255),
                                       number_of_programs INTEGER,
                                       has_delay_start BOOLEAN,
                                       has_display BOOLEAN
);

CREATE TABLE attributes_microwave_oven (
                                           product_id INTEGER UNIQUE REFERENCES products(product_id),
                                           power_output INTEGER,
                                           cooking_time_range VARCHAR(255),
                                           number_of_programs INTEGER,
                                           has_timer BOOLEAN,
                                           has_display BOOLEAN
);

CREATE TABLE attributes_toaster (
                                    product_id INTEGER UNIQUE REFERENCES products(product_id),
                                    power_output INTEGER,
                                    number_of_slots INTEGER,
                                    has_timer BOOLEAN,
                                    has_bread_sensor BOOLEAN,
                                    has_display BOOLEAN
);

CREATE TABLE attributes_blender (
                                    product_id INTEGER UNIQUE REFERENCES products(product_id),
                                    power_output INTEGER,
                                    capacity DECIMAL(8,2),
                                    number_of_speeds INTEGER,
                                    has_timer BOOLEAN,
                                    has_display BOOLEAN
);

CREATE TABLE attributes_coffee_maker (
                                         product_id INTEGER UNIQUE REFERENCES products(product_id),
                                         water_capacity DECIMAL(8,2),
                                         coffee_capacity DECIMAL(8,2),
                                         number_of_programs INTEGER,
                                         has_timer BOOLEAN,
                                         has_display BOOLEAN
);

CREATE TABLE attributes_iron (
                                 product_id INTEGER UNIQUE REFERENCES products(product_id),
                                 power_output INTEGER,
                                 number_of_settings INTEGER,
                                 has_timer BOOLEAN,
                                 has_display BOOLEAN,
                                 has_steam_function BOOLEAN
);

CREATE TABLE attributes_vacuum_cleaner (
                                           product_id INTEGER UNIQUE REFERENCES products(product_id),
                                           power_output INTEGER,
                                           cleaning_area DECIMAL(8,2),
                                           number_of_modes INTEGER,
                                           has_timer BOOLEAN,
                                           has_display BOOLEAN
);

CREATE TABLE attributes_air_conditioner (
                                            product_id INTEGER UNIQUE REFERENCES products(product_id),
                                            cooling_capacity DECIMAL(8,2),
                                            energy_class VARCHAR(255),
                                            number_of_modes INTEGER,
                                            has_timer BOOLEAN,
                                            has_display BOOLEAN
);

CREATE TABLE attributes_heater (
                                   product_id INTEGER UNIQUE REFERENCES products(product_id),
                                   power_output INTEGER,
                                   temperature_range VARCHAR(255),
                                   number_of_modes INTEGER,
                                   has_timer BOOLEAN,
                                   has_display BOOLEAN
);

CREATE TABLE attributes_dehumidifier (
                                         product_id INTEGER UNIQUE REFERENCES products(product_id),
                                         capacity DECIMAL(8,2),
                                         energy_class VARCHAR(255),
                                         number_of_modes INTEGER,
                                         has_timer BOOLEAN,
                                         has_display BOOLEAN
);

CREATE TABLE attributes_humidifier (
                                       product_id INTEGER UNIQUE REFERENCES products(product_id),
                                       capacity DECIMAL(8,2),
                                       energy_class VARCHAR(255),
                                       number_of_modes INTEGER,
                                       has_timer BOOLEAN,
                                       has_display BOOLEAN
);



