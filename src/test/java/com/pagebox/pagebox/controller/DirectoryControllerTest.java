package com.pagebox.pagebox.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.pagebox.pagebox.model.entity.Directory;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = Directory.class) // Somente o PedidoController será carregado
@AutoConfigureMockMvc(addFilters = false)
public class DirectoryControllerTest {
    static String PEDIDO_API = "/api/diretorio";

    @Autowired
    private MockMvc mockMvc;

}
