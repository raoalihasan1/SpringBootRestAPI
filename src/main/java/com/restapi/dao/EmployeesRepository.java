package com.restapi.dao;

import com.restapi.entities.Employees;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends CrudRepository<Employees, Long> {
}
