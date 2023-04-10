package com.restapi.controllers;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.dao.EmployeesService;
import com.restapi.entities.Employees;
import com.restapi.entities.JobTitle;
import com.restapi.entities.Offices;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeesControllerApi.class)
class EmployeesControllerApiTest {

    private final Offices office = new Offices("London Bridge", null, "London", null, "SE1 9BG", "02083857912");
    private final Employees employee = new Employees("John", "Doe", "john.doe@example.com", office, JobTitle.President);
    private final Employees employee1 = new Employees("Jane", "Smith", "jane.smith@example.com", office, JobTitle.VPSales);

    @MockBean
    private EmployeesService employeesService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testGetEmployees() throws Exception {
        List<Employees> employeesList = new ArrayList<>();
        employeesList.add(employee);
        employeesList.add(employee1);

        when(employeesService.getEmployees()).thenReturn(employeesList);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(employeesList.size())))
                .andExpect(jsonPath("$[0].employeeID", is((int) employee.getEmployeeID())))
                .andExpect(jsonPath("$[0].firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$[1].employeeID", is((int) employee1.getEmployeeID())))
                .andExpect(jsonPath("$[1].firstName", is(employee1.getFirstName())));

        verify(employeesService, times(1)).getEmployees();
    }

    @Test
    void testGetEmployee() throws Exception {
        when(employeesService.getEmployee(Mockito.anyLong())).thenReturn(employee);

        mockMvc.perform(get("/api/employees/{id}", employee.getEmployeeID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeID", is((int) employee.getEmployeeID())))
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())));

        verify(employeesService, times(1)).getEmployee(Mockito.anyLong());
    }

    @Test
    void testGetEmployeeNotFound() throws Exception {
        when(employeesService.getEmployee(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/api/employees/{id}", Mockito.anyLong()))
                .andExpect(status().isNotFound());

        verify(employeesService, times(1)).getEmployee(Mockito.anyLong());
    }

    @Test
    void testAddEmployee() throws Exception {
        mapper.disable(MapperFeature.USE_ANNOTATIONS);

        when(employeesService.insertEmployee(Mockito.any(Employees.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeID").value(employee.getEmployeeID()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()));

        verify(employeesService, times(1)).insertEmployee(Mockito.any(Employees.class));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        mapper.disable(MapperFeature.USE_ANNOTATIONS);

        when(employeesService.updateEmployee(Mockito.anyLong(), Mockito.any(Employees.class))).thenReturn(employee1);

        mockMvc.perform(put("/api/employees/{id}", employee.getEmployeeID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employee1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeID").value((int) employee.getEmployeeID()))
                .andExpect(jsonPath("$.firstName").value(employee1.getFirstName()));

        verify(employeesService, times(1)).updateEmployee(Mockito.anyLong(), Mockito.any(Employees.class));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/employees/{id}", Mockito.anyLong()))
                .andExpect(status().isOk());

        verify(employeesService, times(1)).removeEmployee(Mockito.anyLong());
    }

}