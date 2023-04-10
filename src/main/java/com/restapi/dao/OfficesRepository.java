package com.restapi.dao;

import com.restapi.entities.Offices;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficesRepository extends CrudRepository<Offices, Long> {
}
