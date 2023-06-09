package com.example.demo;

import com.example.demo.controller.Prac02Controller;
import com.example.demo.model.Board;
import com.example.demo.model.SearchParam;
import com.example.demo.service.Prac02Service;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// http://localhost:3000/docs/api-doc.html
// PreetyPrint 적용하기

// 나머지 커스텀해서 전부 출력하기.
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class Prac02ControllerTest {

    // JUnit5

    private MockMvc mockMvc;
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test   // CreateTest
    public void createBoardTest() throws Exception {
        String requestJson = "{\"title\": \"게시물 제목\", \"content\": \"게시물 내용\"}";

        this.mockMvc.perform(post("/boards/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("create-test",
                        requestFields(
                                fieldWithPath("title").description("글 제목"),
                                fieldWithPath("content").description("글 내용")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                        )
                ));
    }


    @Test   // Read Test
    public void getBoardTest() throws Exception{
        this.mockMvc.perform(get("/boards/13").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-test"));
    }

    @Test   // Update Test
    public void reWriteBoardTest() throws Exception{

        String requestJson = "{\"title\": \"게시물 수정 제목\", \"content\": \"게시물 수정 내용\"}";


        this.mockMvc.perform(put("/boards/17")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("put-test",
                        requestFields(
                                fieldWithPath("title").description("글 제목"),
                                fieldWithPath("content").description("글 내용")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
                        )
                ));
    }

    @Test   // Delete Test
    void deleteBoardTest() throws Exception{
        this.mockMvc.perform(delete("/boards/36").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("delete-test"));
    }

    @Test
    void boardListTest() throws Exception{
        this.mockMvc.perform(get("/boards/bbslist").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("getlist-test"));
    }






}
