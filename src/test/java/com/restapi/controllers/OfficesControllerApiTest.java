package com.restapi.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.dao.OfficesService;
import com.restapi.entities.Offices;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@WebMvcTest(OfficesControllerApi.class)
public class OfficesControllerApiTest {

    private final Offices  office = new Offices("London Bridge", null, "London", null, "SE1 9BG", "02083857912");
    private final Offices office1 = new Offices("London Eye", null, "London", null, "SE1 7PB", "02079678021");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfficesService officesService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGetAllOffices() throws Exception {
        List<Offices> officesList = new ArrayList<>();
        officesList.add(office);
        officesList.add(office1);

        when(officesService.getOffices()).thenReturn(officesList);

        mockMvc.perform(get("/api/offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(officesList.size())))
                .andExpect(jsonPath("$[0].officeID", is((int) office.getOfficeID())))
                .andExpect(jsonPath("$[0].addressLineOne", is(office.getAddressLineOne())))
                .andExpect(jsonPath("$[1].officeID", is((int) office1.getOfficeID())))
                .andExpect(jsonPath("$[1].addressLineOne", is(office1.getAddressLineOne())));

        verify(officesService, times(1)).getOffices();
    }

    @Test
    public void testGetOffice() throws Exception {
        when(officesService.getOffice(Mockito.anyLong())).thenReturn(office);

        mockMvc.perform(get("/api/offices/{id}", office.getOfficeID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeID", is((int) office.getOfficeID())))
                .andExpect(jsonPath("$.addressLineOne", is(office.getAddressLineOne())));

        verify(officesService, times(1)).getOffice(Mockito.anyLong());
    }

    @Test
    public void testGetOfficeNotFound() throws Exception {
        when(officesService.getOffice(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/api/offices/{id}", Mockito.anyLong()))
                .andExpect(status().isNotFound());

        verify(officesService, times(1)).getOffice(Mockito.anyLong());
    }

    @Test
    public void testAddOffice() throws Exception {
        when(officesService.insertOffice(Mockito.any(Offices.class))).thenReturn(office);

        mockMvc.perform(post("/api/offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(office)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeID").value(office.getOfficeID()))
                .andExpect(jsonPath("$.addressLineOne").value(office.getAddressLineOne()));

        verify(officesService, times(1)).insertOffice(Mockito.any(Offices.class));
    }

    @Test
    public void testUpdateOffice() throws Exception {
        when(officesService.updateOffice(Mockito.anyLong(), Mockito.any(Offices.class))).thenReturn(office1);

        mockMvc.perform(put("/api/offices/{id}", office.getOfficeID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(office)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeID", is((int) office.getOfficeID())))
                .andExpect(jsonPath("$.addressLineOne", is(office1.getAddressLineOne())));

        verify(officesService, times(1)).updateOffice(Mockito.anyLong(), Mockito.any(Offices.class));
    }

    @Test
    public void testDeleteOffice() throws Exception {
        mockMvc.perform(delete("/api/offices/{id}", Mockito.anyLong()))
                .andExpect(status().isOk());

        verify(officesService, times(1)).removeOffice(Mockito.anyLong());
    }

}
