package com.poc.spring.batch.SpringBatch.reader;

import com.poc.spring.batch.SpringBatch.model.Employee;
import com.poc.spring.batch.SpringBatch.utils.ErrorFlag;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

@Component
public class EmployeeReader implements ItemReader<Employee> {

    @Autowired
    private ErrorFlag errorFlag;

    private int nextEmployeeIndex;
    private List<Employee> employeeList;

    public EmployeeReader() {
       initialize();
    }

    public void initialize() {
        Employee tony = new Employee();
        tony.setId(UUID.randomUUID().toString());
        tony.setFirstName("Nico");
        tony.setLastName("Robin");

        Employee tony2 = new Employee();
        tony2.setId(UUID.randomUUID().toString());
        tony2.setFirstName("Tony Tony");
        tony2.setLastName("Chopper");

        Employee nick = new Employee();
        nick.setId(UUID.randomUUID().toString());
        nick.setFirstName("Roronoa");
        nick.setLastName("Zoro");


        employeeList = Collections.unmodifiableList(Arrays.asList(tony, tony2, nick));
        nextEmployeeIndex = 0;
    }

/*    public List<Employee> initializeList() {
        Employee tony = new Employee();
        tony.setId("1");
        tony.setFirstName("Nico");
        tony.setLastName("Robin");

        Employee nick = new Employee();
        nick.setId("2");
        nick.setFirstName("Roronoa");
        nick.setLastName("Zoro");

        Employee ian = new Employee();
        ian.setId("3");
        ian.setFirstName("Vinsmoke");
        ian.setLastName("Sanji");

        employeeList = Collections.unmodifiableList(Arrays.asList(tony, nick, ian));
        nextEmployeeIndex = 0;
        return employeeList;
    }*/

    @Override
    public Employee read() throws Exception {

        Employee employee =null;
        //employeeList = initializeList();
        //throw new NullPointerException();
        if(nextEmployeeIndex < employeeList.size()){
            sleep(5000);
            employee = employeeList.get(nextEmployeeIndex);
            System.out.println("Reading employee Data : " + employee.toString());
            nextEmployeeIndex++;
        }
        return employee;
    }

}
