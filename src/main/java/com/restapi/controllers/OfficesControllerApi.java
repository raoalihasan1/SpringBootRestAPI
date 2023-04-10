package com.restapi.controllers;

import com.restapi.dao.OfficesService;
import com.restapi.entities.Offices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "api/offices")
public class OfficesControllerApi {

    @Autowired
    private OfficesService officesService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Offices> getAllOffices() {
        return officesService.getOffices();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Offices> getOffice(@PathVariable long id) {
        try {
            return new ResponseEntity<>(officesService.getOffice(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Offices addOffice(@RequestBody @Validated Offices office) { return officesService.insertOffice(office); }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Offices> updateOffice(@PathVariable long id, @RequestBody @Validated Offices office) {
        try {
            return new ResponseEntity<>(officesService.updateOffice(id, office), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOffice(@PathVariable long id) {
        officesService.removeOffice(id);
    }

}
