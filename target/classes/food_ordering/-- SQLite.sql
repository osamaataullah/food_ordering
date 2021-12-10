-- SQLite
SELECT * from rating 
SELECT * FROM orders;
SELECT * FROM user;
SELECT * FROM restaurant;
SELECT * FROM city_area_distance;
SELECT * FROM menu;
SELECT * FROM city_area_distance;
SELECT * FROM delivery_charges;

ALTER TABLE restaurant ADD city_area TEXT DEFAULT "okhla" NOT NULL;

DELETE FROM restaurant where restaurant_name=="";

INSERT INTO restaurant(restaurant_name,city, city_area) VALUES("Dominos","delhi","okhla");

ALTER TABLE user ADD city_area TEXT DEFAULT "okhla" NOT NULL;

CREATE TABLE city_area_distance(city TEXT NOT NULL, area1 TEXT NOT NULL, area2 TEXT NOT NULL, distance INTEGER NOT NULL)

INSERT INTO city_area_distance  VALUES("Indore","REGAL","Palasia",20);

INSERT INTO menu(menu_name,price,estimated_time,restaurant_id) VALUES('Dominos Pizza',200,10,8);

SELECT * FROM restaurant WHERE city="Indore"


CREATE TABLE delivery_charges(distance INTEGER NOT NULL, delivery_charges INTEGER NOT NULL);

INSERT INTO delivery_charges VALUES(0,0);
INSERT INTO delivery_charges VALUES(5,30);
INSERT INTO delivery_charges VALUES(10,60);
INSERT INTO delivery_charges VALUES(20,80);

DELETE FROM user WHERE name="rishav";

SELECT distance FROM city_area_distance where area1="Vijay Nagar" and area2="Vijay Nagar"

DELETE from city_area_distance where city ="Vijay Nagar";

DELETE FROM orders where user_id=15;

INSERT INTO menu(menu_name, price, estimated_time, restaurant_id) VALUES('Large Pizza', 200, 1,6);



select * from orders as o inner join menu as m on o.menu_id = m.menu_id where order_status = 'DELIVERED' and user_id = 15