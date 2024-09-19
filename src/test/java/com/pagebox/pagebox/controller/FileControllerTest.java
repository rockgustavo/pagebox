package com.pagebox.pagebox.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.pagebox.pagebox.model.entity.File;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = File.class) // Somente o PedidoController será carregado
@AutoConfigureMockMvc(addFilters = false)
public class FileControllerTest {
    static String PEDIDO_API = "/api/arquivo";

    @Autowired
    private MockMvc mockMvc;

}
