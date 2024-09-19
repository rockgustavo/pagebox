package com.pagebox.pagebox.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagebox.pagebox.model.entity.Directory;
import com.pagebox.pagebox.rest.controller.FileController;
import com.pagebox.pagebox.rest.dto.FileDTO;
import com.pagebox.pagebox.service.FileService;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FileController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FileControllerTest {
        static String PEDIDO_API = "/api/arquivo";

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private FileService fileService;

        private static final String FILE_API = "/api/arquivo";

        @Test
        public void deveSalvarFileComSucesso() throws Exception {
                // Cenário
                FileDTO fileDTO = FileDTO.builder()
                                .name("dir1")
                                .content("teste")
                                .directory(Directory.builder()
                                                .id(1L)
                                                .build())
                                .build();

                FileDTO fileSaved = FileDTO.builder()
                                .name("dir1")
                                .content("teste")
                                .directory(Directory.builder()
                                                .id(1L)
                                                .build())
                                .build();

                BDDMockito.given(fileService.createFile(Mockito.any(FileDTO.class)))
                                .willReturn(fileSaved);

                String JSON = new ObjectMapper().writeValueAsString(fileDTO);

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .post(FILE_API)
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
        public void deveAtualizarFileComSucesso() throws Exception {
                // Cenário
                Long id = 1L;
                FileDTO fileDTO = FileDTO.builder()
                                .name("file_updated")
                                .content("content_updated")
                                .directory(Directory.builder().id(1L).build())
                                .build();

                FileDTO updatedFile = FileDTO.builder()
                                .name("file_updated")
                                .content("content_updated")
                                .directory(Directory.builder().id(1L).build())
                                .build();

                BDDMockito.given(fileService.updateFile(Mockito.eq(id), Mockito.any(FileDTO.class)))
                                .willReturn(updatedFile);

                String JSON = new ObjectMapper().writeValueAsString(fileDTO);

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .put(FILE_API + "/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(JSON);

                // Verificação
                mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("file_updated"))
                                .andExpect(jsonPath("$.content").value("content_updated"));
        }

        @Test
        public void deveExcluirFileComSucesso() throws Exception {
                // Cenário
                Long id = 1L;

                BDDMockito.doNothing().when(fileService).deleteFile(Mockito.eq(id));

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .delete(FILE_API + "/{id}", id)
                                .accept(MediaType.APPLICATION_JSON);

                // Verificação
                mockMvc.perform(request)
                                .andExpect(status().isNoContent());
        }

        @Test
        public void deveBuscarFilePorIdComSucesso() throws Exception {
                // Cenário
                Long id = 1L;
                FileDTO fileDTO = FileDTO.builder()
                                .name("dir1")
                                .content("conteudo")
                                .directory(Directory.builder()
                                                .id(1L)
                                                .build())
                                .build();

                BDDMockito.given(fileService.getFileById(id))
                                .willReturn(Optional.of(fileDTO));

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .get(FILE_API + "/" + id)
                                .accept(MediaType.APPLICATION_JSON);

                // Verificação
                mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("dir1"))
                                .andExpect(jsonPath("$.content").value("conteudo"));
        }

        @Test
        public void deveBuscarFilePorNomeComSucesso() throws Exception {
                // Cenário
                String nome = "dir1";
                FileDTO fileDTO = FileDTO.builder()
                                .name(nome)
                                .content("conteudo")
                                .directory(Directory.builder()
                                                .id(1L)
                                                .build())
                                .build();

                BDDMockito.given(fileService.getFileByName(nome))
                                .willReturn(Optional.of(fileDTO));

                // Converta o DTO para JSON
                String jsonFileDTO = new ObjectMapper().writeValueAsString(fileDTO);

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .get(FILE_API + "/nome")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFileDTO)
                                .accept(MediaType.APPLICATION_JSON);

                // Verificação
                mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value(nome))
                                .andExpect(jsonPath("$.content").value("conteudo"));
        }

        @Test
        public void deveBuscarTodosFilesComPaginacao() throws Exception {
                // Cenário
                List<FileDTO> files = Arrays.asList(
                                FileDTO.builder()
                                                .name("file1")
                                                .content("content_test1")
                                                .directory(Directory.builder().id(1L).build())
                                                .build(),
                                FileDTO.builder()
                                                .name("file2")
                                                .content("content_test2")
                                                .directory(Directory.builder().id(2L).build())
                                                .build());

                Page<FileDTO> page = new PageImpl<>(files, PageRequest.of(0, 10), 1);

                BDDMockito.given(fileService.getAllFiles(Mockito.any(Pageable.class)))
                                .willReturn(page);

                // Execução
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .get(FILE_API)
                                .param("page", "0")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON);

                // Verificação
                mockMvc.perform(request)
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.[0].name").value("file1"))
                                .andExpect(jsonPath("$.[0].content").value("content_test1"))
                                .andExpect(jsonPath("$.[1].name").value("file2"))
                                .andExpect(jsonPath("$.[1].content").value("content_test2"));
        }

}
