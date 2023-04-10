package com.restapi.dao;

import com.restapi.entities.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public Iterable<Employees> getEmployees() { return employeesRepository.findAll(); }

    public Employees getEmployee(long id) { return employeesRepository.findById(id).get(); }

    public Employees insertEmployee(Employees employee) { return employeesRepository.save(employee); }

    public Employees updateEmployee(long id, Employees employee) {
        boolean employeeExists = employeesRepository.findById(id).isPresent();
        if(employeeExists) {
            Employees emp = employeesRepository.findById(id).get();
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            emp.setEmailAddress(employee.getEmailAddress());
            emp.setEmployeeOffice(employee.getEmployeeOffice());
            emp.setJobTitle(employee.getJobTitle());
            return employeesRepository.save(emp);
        }
        return null;
    }

    public void removeEmployee(long id) { employeesRepository.deleteById(id); }

}
