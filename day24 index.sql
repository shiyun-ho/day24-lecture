use leisure; 

/*Create an index for first name*/
create index idx_firstname
on employee(first_name); 

/* view it at schema > leisure> employee> idx_firstname*/
select * from employee;

/*create index for one column*/
create index idx_name
on employee(first_name, last_name);

