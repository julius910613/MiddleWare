--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
insert into customer (id, name, password, personID, email,phone_number) values (0, 'John Smith', '091320117', '2125551212', '690081332@qq.com', '62812618')
insert into customer (id, name, password, personID, email,phone_number) values (1, 'Zequn Li', '081340117', '2126651212','692341332@qq.com', '62812458')
insert into customer (id, name, password, personID, email,phone_number) values (2, 'Xin Wen', '101320711', '2125565465','694561332@qq.com', '62456458')
insert into taxi (id, quanity, taxiname, carregistrationid, prize) values (0, 2, 'Das', '9482222454', 4)
insert into taxi (id, quanity, taxiname, carregistrationid, prize) values (1, 3, 'BMW', '1234522454', 6)
insert into contract (id, customer_id, taxi_id, contract_date) values(0, 1, 1, '2012-10-10')
insert into contract (id, customer_id, taxi_id, contract_date) values(1, 0, 0, '2012-12-10')
insert into contract (id, customer_id, taxi_id, contract_date) values(2, 1, 0, '2013-10-10')
insert into contract (id, customer_id, taxi_id, contract_date) values(3, 0, 1, '2012-1-1')