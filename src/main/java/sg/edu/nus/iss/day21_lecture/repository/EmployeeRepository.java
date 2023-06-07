package sg.edu.nus.iss.day21_lecture.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day21_lecture.model.Dependant;
import sg.edu.nus.iss.day21_lecture.model.Employee;

@Repository
public class EmployeeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //INNER JOIN: Finds common entries where employee id is the same for employee and dependent table
    // String findAllSQL = "select e.id as emp_id, e.first_name, e.last_name, e.salary, " +
    //         " d.id dep_id, d.full_name, d.birthdate, d.relationship " +
    //         " from employee e " +
    //         " inner join dependent d " +
    //         " on e.id = d.employee_id ";

    //change to left join to find out new employee entries if dependent does not exist
    String findAllSQL = "select e.id as emp_id, e.first_name, e.last_name, e.salary, " +
            " d.id dep_id, d.full_name, d.birthdate, d.relationship " +
            " from employee e " +
            " left join dependent d " +
            " on e.id = d.employee_id ";

    // String findByIdSQL = "select e.id as emp_id, e.first_name, e.last_name, e.salary, " +
    //         " d.id dep_id, d.full_name, d.birthdate, d.relationship " +
    //         " from employee e " +
    //         " inner join dependent d " +
    //         " on e.id = d.employee_id " +
    //         " where e.id = ? ";


    //because the record doesn't show previously because depedent
    String findByIdSQL = "select e.id as emp_id, e.first_name, e.last_name, e.salary, " +
            " d.id dep_id, d.full_name, d.birthdate, d.relationship " +
            " from employee e " +
            " left join dependent d " +
            " on e.id = d.employee_id " +
            " where e.id = ? ";

    String insertSQL = "insert into employee (first_name, last_name, salary) values (?, ?, ?)";

    String updateSQL = "update employee set first_name = ?, last_name = ?, salary = ? where id = ? ";

    String deleteSQL = "delete from employee where id = ?";

    public Boolean save(Employee employee) {
        Boolean bSaved = false;

        PreparedStatementCallback<Boolean> psc = new PreparedStatementCallback<Boolean>() {

            @Override
            @Nullable
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getSalary());
                Boolean rslt = ps.execute();
                return rslt;
            }

        };

        bSaved = jdbcTemplate.execute(insertSQL, psc);

        return bSaved;
    }

    public int update(Employee employee) {
        int iUpdated = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            // "update employee set first_name = 1, last_name = 2, salary = 3 where id = 4 "
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getSalary());
                ps.setInt(4, employee.getId());
            }
        };

        iUpdated = jdbcTemplate.update(updateSQL, pss);

        return iUpdated;
    }

    public int delete(Integer id) {
        int bDeleted = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

                ps.setInt(1, id);
            }
        };

        bDeleted = jdbcTemplate.update(deleteSQL, pss);

        return bDeleted;
    }

    //find all common entries where employee id is the same between employee table and dependent
    public List<Employee> findAll() {
        //create a new array list instance of entity employee 'employees'
        List<Employee> employees = new ArrayList<Employee>();

        //use jdbcTemplate to carry out sql query along with the result set
        //resultSetExttactor helps to extract results without worrying about exception handling
        employees = jdbcTemplate.query(findAllSQL, new ResultSetExtractor<List<Employee>>() {

            //method to now handle the result set
            //checks if there is an existing record
            @Override
            //method to extract data from result set
            public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
                //temp list 1: store list of employees temporarily 1
                //function: ????
                List<Employee> emps = new ArrayList<Employee>();


                //while there is the next row to parse through in result set (aka the List 'employees')
                while(rs.next()) {
                    // e.id as emp_id, e.first_name, e.last_name, e.salary,
                    //instantiate the entity 
                    Employee employee = new Employee();
                    //set all variables required for entity
                    employee.setId(rs.getInt("emp_id"));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setSalary(rs.getInt("salary"));
                    employee.setDependants(new ArrayList<Dependant>());

                    // d.id dep_id, d.full_name, d.birth_date, d.relationship
                    //instantiate the entity dependant
                    Dependant dependant = new Dependant();
                    //set all variables
                    dependant.setId(rs.getInt("dep_id"));
                    dependant.setFullName(rs.getString("full_name"));
                    dependant.setRelationship(rs.getString("relationship"));
                    dependant.setBirthdate(rs.getDate("birthdate"));

                    //if tempList1 is empty: aka NO records in temporary list of employees
                    if (emps.size() == 0) {
                        //add dependant to EMPLOYEE entity-> get the dependants variables
                        employee.getDependants().add(dependant);
                        //add employee entity to tempList1
                        emps.add(employee);
                    } 
                    //else if tempList1 DOES HAVE record
                    else {
                        //create another tempList
                        //BUT this time we will store just the id
                        List<Employee> tmpList = emps.stream().filter(e -> e.getId() == employee.getId()).collect(Collectors.toList());

                        //if tempList2 (ie. the list of ids is NOT EMPTY) 
                        //IE if the list of IDs is not zero
                        if (tmpList.size() > 0) {
                            // append to dependant list for the found employee
                            //idx will be the index of tempList1 (aka the employee and dependent merged entity)
                                //which could be gotten from the 
                                //tmpList.get(0) -> element at index 0
                                //emps.indexOf(element at index 0 of tmpList)
                                //idx will be the index of temporary list of ids tmpList
                            int idx = emps.indexOf(tmpList.get(0));

                            //if the index of temporary list of Ids is 0 or more (aka if we are now at position 0 of id list)
                            if (idx >= 0) {
                                //temporary list of employees get the index position (0)
                                    //get dependants
                                        //add dependant entity to dependants in temp list of employees
                                emps.get(idx).getDependants().add(dependant);
                            }
                        } 
                        //ELSE if list of IDs is ZERO aka no id yet
                        else {
                            // if the employee record in not found in the list of employees

                            // add a new employee record together with the dependant information
                            employee.getDependants().add(dependant);
                            //add employee entity to the tmepList of employees
                            emps.add(employee);
                        }
                    }
                }

                //return tempList of employees
                return emps;
            }
        });

        //return employees list
        return employees;
    }

    public Employee findByEmployeeId(Integer employeeId) {
        Employee employee = new Employee();

        employee = jdbcTemplate.query(findByIdSQL, new ResultSetExtractor<Employee>() {

            @Override
            public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
                Employee emp = new Employee();

                while(rs.next()) {
                    // e.id as emp_id, e.first_name, e.last_name, e.salary,
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("emp_id"));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setSalary(rs.getInt("salary"));
                    employee.setDependants(new ArrayList<Dependant>());

                    // d.id dep_id, d.full_name, d.birth_date, d.relationship
                    Dependant dependant = new Dependant();
                    dependant.setId(rs.getInt("dep_id"));
                    dependant.setFullName(rs.getString("full_name"));
                    dependant.setRelationship(rs.getString("relationship"));
                    dependant.setBirthdate(rs.getDate("birthdate"));

                    if (rs.isFirst()) {
                        emp = employee;
                        emp.getDependants().add(dependant);
                    } else {
                        emp.getDependants().add(dependant);
                    }

                }

                return emp;
            }
        }, employeeId);

        return employee;
    }

}
