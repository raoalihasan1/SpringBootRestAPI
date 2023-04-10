package com.restapi.controllers;

import com.restapi.dao.EmployeesService;
import com.restapi.entities.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "api/employees")
public class EmployeesControllerApi {

    @Autowired
    private EmployeesService employeesService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Employees> getEmployees() { return employeesService.getEmployees(); }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employees> getEmployee(@PathVariable long id) {
        try {
            return new ResponseEntity<>(employeesService.getEmployee(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Employees addEmployee(@RequestBody @Validated Employees employee) { return employeesService.insertEmployee(employee); }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employees> updateEmployee(@PathVariable long id, @RequestBody @Validated Employees employee) {
        try {
            return new ResponseEntity<>(employeesService.updateEmployee(id, employee), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable long id) { employeesService.removeEmployee(id); }

}
