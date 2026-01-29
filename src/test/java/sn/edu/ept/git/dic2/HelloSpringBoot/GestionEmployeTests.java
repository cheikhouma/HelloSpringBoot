package sn.edu.ept.git.dic2.HelloSpringBoot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sn.edu.ept.git.dic2.HelloSpringBoot.entities.Employe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class GestionEmployeTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    Logger log = LoggerFactory.getLogger(this.getClass());
    private String url = "/employes";

    @BeforeAll
    public static void initClass() {
        Logger log = LoggerFactory.getLogger(GestionEmployeTests.class);
        log.info("Init class");
    }

    @BeforeEach
    public void initTest() {
        log.info("~~~~~~~~~~~~( Init test )~~~~~~~~~~~~");
    }

    @Test
    public void testListEmploye() throws Exception {
        log.info("testListEmploye");

        MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        List<Employe> employes = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>(){});

        for (Employe employe : employes) {
            log.info(employe.getNom());
        }

        assertFalse(employes.isEmpty());

    }

    @Test
    public void testAjout() throws Exception {
        log.info("Test ajout d'un employé.");

        Employe e = new Employe();
        e.setPrenom("Abdoulaye Sy");
        e.setNom("Ndaw");
        e.setSalary(1000.0);

        String data = objectMapper.writeValueAsString(e);

        MvcResult result = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isOk())
                .andReturn();

        Employe employee = objectMapper.readValue(
                result.getResponse().getContentAsString(), Employe.class);
        log.info(employee.getNom());
        log.info(String.valueOf(employee.getId()));
    }

    @Test
    public void testModification() throws RuntimeException {
        log.info("Test modification d'un employé.");
//        throw new RuntimeException();
    }

    @Test
    public void testDelete() {
        log.info("Test suppression d'un employé.");
    }
}
