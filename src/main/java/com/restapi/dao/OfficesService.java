package com.restapi.dao;

import com.restapi.entities.Offices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficesService {

    @Autowired
    private OfficesRepository officesRepository;

    public Iterable<Offices> getOffices() { return officesRepository.findAll(); }

    public Offices getOffice(long id) { return officesRepository.findById(id).get(); }

    public Offices insertOffice(Offices office) { return officesRepository.save(office); }

    public Offices updateOffice(long id, Offices office) {
        boolean officeExists = officesRepository.findById(id).isPresent();
        if(officeExists) {
            Offices off = officesRepository.findById(id).get();
            off.setAddressLineOne(office.getAddressLineOne());
            off.setAddressLineTwo(office.getAddressLineTwo());
            off.setCity(office.getCity());
            off.setState(office.getState());
            off.setPostCode(office.getPostCode());
            off.setPhoneNumber(office.getPhoneNumber());
            return officesRepository.save(off);
        }
        return null;
    }

    public void removeOffice(long id) { officesRepository.deleteById(id); }

}