use leisure; 

select * from employee; 

select * from dependent; 

insert into employee (first_name, last_name, salary) values ('darren', 'low', 9000);
insert into employee (first_name, last_name, salary) values ('darren', 'liw', 19000);
insert into employee (first_name, last_name, salary) values ('darryl', 'tan', 1000);
insert into employee (first_name, last_name, salary) values ('mirrada', 'tan', 900);
insert into employee (first_name, last_name, salary) values ('blue', 'ho', 8000);
insert into employee (first_name, last_name, salary) values ('cool', 'lim', 1900);


select * from employee; 
select * from dependent; 

insert into dependent (employee_id, full_name, relationship, birthdate) values (1, 'sky low', 'daughther', '2000-12-12');
insert into dependent (employee_id, full_name, relationship, birthdate) values (2, 'park tan', 'son', '2010-01-25');
insert into dependent (employee_id, full_name, relationship, birthdate) values (3, 'ariana low', 'daughther', '2011-02-16');
insert into dependent (employee_id, full_name, relationship, birthdate) values (4, 'boy low', 'daughther', '2000-12-12');
insert into dependent (employee_id, full_name, relationship, birthdate) values (5, 'simi tan', 'son', '2010-01-25');
insert into dependent (employee_id, full_name, relationship, birthdate) values (6, 'ariana kao', 'daughther', '2011-02-16');

insert into employee(first_name, last_name, salary) values ('boboo', 'lim', 1990);

update employee set first_name = 'boingboing', last_name = 'tan', salary='2500' where id = 7;

/*combines distinct entry of */

select e.id as emp_id, e.first_name, e.last_name, e.salary,
d.id as dep_id, d.full_name, d.birthdate, d.relationship
from employee e
inner join dependent d
on e.id = d.employee_id; 

/*select by id*/
select e.id as emp_id, e.first_name, e.last_name, e.salary,
d.id dep_id, d.full_name, d.birthdate, d.relationship
from employee e
inner join dependent d
on e.id = d.employee_id
where e.id=? ;

-- insert into employee (first_name, last_name, salary) values (?, ?, ?) 

-- delete from employee where id = ?

-- update employee set first_name =?, last_name = ?, salary =?


/*homework dependent*/
/*INNER JOIN*/
select d.id as dep_id, d.full_name, d.birthdate, d.relationship,
e.id as emp_id, e.first_name, e.last_name, e.salary
from dependent d
inner join employee e
on e.id = d.employee_id;

/*distinct where id =?/ */
select d.id as dep_id, d.full_name, d.birthdate, d.relationship,
e.id as emp_id, e.first_name, e.last_name, e.salary
from dependent d
inner join employee e
on e.id = d.employee_id
where d.id = 7; 

insert into employee(first_name, last_name, salary) values ('nana', 'butsan', 1920);
select * from employee; 
/*Insert record into dependent*/
insert into dependent (employee_id, full_name, relationship, birthdate) values (8, 'brianna tan', 'daughther', '2000-12-25');

delete from dependent where id = 7; 

update dependent set full_name = 'Brianna Tannz', relationship = 'son', birthdate = '2000-11-25' where id = 10;
