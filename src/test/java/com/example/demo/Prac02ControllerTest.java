package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// http://localhost:3000/docs/api-doc.html
// PreetyPrint 적용하기

// 나머지 커스텀해서 전부 출력하기.
@SpringBootTest
@AutoConfigureRestDocs  // rest docs 자동 설정
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class Prac02ControllerTest {

    // JUnit5

    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;
    private WebApplicationContext context;
    private RestDocumentationContextProvider restDocumentation;


//    @Before
//    public void setUp() {
//        // spring rest docs를 위한 양식 설정
//        this.document = document(
//                "{class-name}/{method-name}",   // snippet 생성시 이름을 자동으로 {class-name}/{method-name} 로 저장
//                preprocessRequest(
//        /*
//        preprocessRequest : request snippet 생성시 설정
//        modifyUris() : 기본 localhost:8080 으로 뜨는 host 이름을 내마음대로 바꿀수있다.
//        prettyPrint() : request snippet생성시 prettyPrint 된다
//         */
//                        modifyUris()
//                                .scheme("http")
//                                .host("berrrr.demopage.com")
//                                .removePort(),
//                        prettyPrint()),
//                preprocessResponse(prettyPrint())
//        /*
//        preprocessResponse : response snippet 생성시 설정
//        prettyPrint() : response snippet 생성시 pretty Print 된다.
//         */
//        );
//
//        // mockMvc 실행시 spring rest docs 설정을 자동 적용
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
//                .apply(documentationConfiguration(this.restDocumentation)
//                        .operationPreprocessors()
//                        .withRequestDefaults(prettyPrint())
//                        .withResponseDefaults(prettyPrint())
//                )
//                .alwaysDo(document)
//                .build();
//    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .build();
    }

    @Test   // Create Test
    @Transactional  // 테스트 완료 후 rollback
    public void createBoardTest() throws Exception {
        String requestJson = "{\"title\": \"게시물 제목\", \"content\": \"게시물 내용\", \"author\":\"작성자\"}";

        this.mockMvc.perform(post("/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("create-test",
                        requestFields(
                                fieldWithPath("title").description("글 제목").optional(),
                                fieldWithPath("content").description("글 내용").optional(),
                                fieldWithPath("author").description("작성자").optional()
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("timestamp").description("수행 시간"),
                                fieldWithPath("status").description("수행 결과 상태")
                        )
                ));
    }


    @Test   // Read Test
    public void getBoardTest() throws Exception{
        String requestJson = "{\"bbsseq\": \"게시물 번호\"}";

        this.mockMvc.perform(get("/boards/118")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(document("get-test",
                        requestFields(
                                fieldWithPath("bbsseq").description("글 번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("bbsseq").description("글 번호"),
                                fieldWithPath("title").description("글 제목"),
                                fieldWithPath("content").description("글 내용"),
                                fieldWithPath("author").description("작성자"),
                                fieldWithPath("del").description("삭제여부")
                        )
                        ));
    }

    @Test   // Update Test
    @Transactional  // 테스트 완료 후 rollback
    public void reWriteBoardTest() throws Exception{

        String requestJson = "{\"title\": \"게시물 수정 제목\", \"content\": \"게시물 수정 내용\", \"author\":\"작성자\"}";


        this.mockMvc.perform(put("/boards/118")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("put-test",
                        requestFields(
                                fieldWithPath("title").description("글 제목").optional(),
                                fieldWithPath("content").description("글 내용").optional(),
                                fieldWithPath("author").description("작성자").optional()
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("timestamp").description("수행 시간"),
                                fieldWithPath("status").description("수행 결과 상태")
                        )
                ));
    }

    @Test   // Delete Test
    @Transactional  // 테스트 완료 후 rollback
    void deleteBoardTest() throws Exception{
        String requestJson = "{\"bbsseq\": \"게시물 번호\"}";

        this.mockMvc.perform(delete("/boards/118")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("delete-test",
                        requestFields(
                                fieldWithPath("bbsseq").description("글 번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("timestamp").description("수행 시간"),
                                fieldWithPath("status").description("수행 결과 상태")
                        )
                        ));
    }

    @Test
    void boardListTest() throws Exception{
        String requestJson = "{\"search\": \"abc\", \"pageNum\": \"1\", \"exposedCount\": \"1\"}";

        this.mockMvc.perform(get("/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("getlist-test",
                        requestFields(
                                fieldWithPath("search").description("검색 내용 (default = ' ')"),
                                fieldWithPath("pageNum").description("페이지 (default = 1)"),
                                fieldWithPath("exposedCount").description("페이지당 노출 개수 (default = 5)")
                        ),
                        responseFields(
                                fieldWithPath("searchParam.search").description("검색 내용"),
                                fieldWithPath("searchParam.pageNum").description("페이지"),
                                fieldWithPath("searchParam.exposedCount").description("페이지당 노출 개수"),
                                fieldWithPath("bbsList[].bbsseq").description("게시물 번호"),
                                fieldWithPath("bbsList[].title").description("게시물 제목"),
                                fieldWithPath("bbsList[].content").description("게시물 내용"),
                                fieldWithPath("bbsList[].del").description("삭제 여부"),
                                fieldWithPath("bbsList[].author").description("작성자"),
                                fieldWithPath("totalCount").description("검색된 게시물의 총 개수")
                        )
                ));
    }






}
