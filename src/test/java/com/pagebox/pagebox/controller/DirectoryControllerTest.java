package com.pagebox.pagebox.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagebox.pagebox.rest.controller.DirectoryController;
import com.pagebox.pagebox.rest.dto.DirectoryDTO;
import com.pagebox.pagebox.service.DirectoryService;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DirectoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DirectoryControllerTest {
        private static final String API = "/api/diretorio";

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private DirectoryService directoryService;

        @Test
        public void deveSalvarDirectoryComSucesso() throws Exception {
                // Cenário
                DirectoryDTO directoryDTO = DirectoryDTO.builder()
                                .name("dir1")
                                .build();

                DirectoryDTO directorySalvo = DirectoryDTO.builder()
                                .name("dir1")
                                .parentDirectory(null)
                                .files(null)
                                .build();

                BDDMockito.given(directoryService.createDirectory(Mockito.any(DirectoryDTO.class)))
                                .willReturn(directorySalvo);

                String JSON = new ObjectMapper().writeValueAsString(directoryDTO);

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .post(API)
                                .param("parentId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(JSON);

                // Verificação
                mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("dir1"));
        }

        @Test
        public void deveAtualizarDirectoryComSucesso() throws Exception {
                // Cenário
                Long id = 1L;
                DirectoryDTO directoryDTO = DirectoryDTO.builder()
                                .name("dir1 updated")
                                .build();

                DirectoryDTO directoryAtualizado = DirectoryDTO.builder()
                                .name("dir1 updated")
                                .parentDirectory(null)
                                .files(null)
                                .build();

                BDDMockito.given(directoryService.updateDirectory(Mockito.eq(id), Mockito.any(DirectoryDTO.class)))
                                .willReturn(directoryAtualizado);

                String JSON = new ObjectMapper().writeValueAsString(directoryDTO);

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .put(API + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(JSON);

                // Verificação
                mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("dir1 updated"));
        }

        @Test
        public void deveExcluirDirectoryComSucesso() throws Exception {
                // Cenário
                Long id = 1L;

                BDDMockito.doNothing().when(directoryService).deleteDirectory(Mockito.eq(id));

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .delete(API + "/{id}", id)
                                .accept(MediaType.APPLICATION_JSON);

                // Verificação
                mockMvc.perform(request)
                                .andExpect(status().isNoContent());
        }

        @Test
        public void deveListarTodosDiretoriosComSucesso() throws Exception {
                List<DirectoryDTO> directories = Arrays.asList(
                                DirectoryDTO.builder().name("dir1").build(),
                                DirectoryDTO.builder().name("dir2").build());

                BDDMockito.given(directoryService.getAllDirectories()).willReturn(directories);

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .get(API)
                                .accept(MediaType.APPLICATION_JSON);

                mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("dir1"))
                                .andExpect(jsonPath("$[1].name").value("dir2"));
        }

}
