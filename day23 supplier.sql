use northwind; 

select * from purchase_order_details; 

select distinct product_id from purchase_order_details;

/*using the in clause, do a subquery and select from product table
with line 5 subquery to *select distinct product_id from purchase_order_details*/
/*from parent table*/
select id, product_code, product_name from products
where id in(select distinct product_id from purchase_order_details);


select * from products;
select * from suppliers;
select * from orders;

/*get distinct supplier id from products then get supplier name*/
select * from products;

/*Selects distinct supplier ids from product table*/
/*Select from suppliers*/
select id, company, last_name, first_name, job_title from suppliers
/* from distinct supplier ids of products*/
/*select (variables from suppliers)*/
where id in (
/*select distinct supplier_ids from products*/
/*it will return list of supplier id that is distinct from product list*/
select distinct supplier_ids from products
); 

/*ie: From unique suppliers id from product list, select details from suppliers*/


/*INNER JOIN between suppliers and products*/
/*select these variables from supplier table*/
select sup.id, sup.company, sup.last_name, sup.first_name, sup.job_title
from suppliers as sup
/*inner join: i.e. id btw suppliers and products match*/
inner join products as prod
/*condition to match -> id of product and supplier*/
on prod.supplier_ids = sup.id; 

select distinct prod.id, prod.product_code, prod.product_name
from products as prod
inner join purchase_order_details as pod
on pod.product_id = prod.id; 

select * from employee where id = 1; 

