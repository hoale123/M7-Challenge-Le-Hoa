package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private LabelRecommendation smithLabelRecommendation;
    private String smithJson;
    private List<LabelRecommendation> allLabelRecommendations = new ArrayList<>();
    private String allLabelRecommendationsJson;

    @Before
    public void setup() throws Exception {
        smithLabelRecommendation = new LabelRecommendation(1,1,1,true);
        smithJson = mapper.writeValueAsString(smithLabelRecommendation);

        LabelRecommendation LabelRecommendation = new LabelRecommendation(1,1,true);
        LabelRecommendation.setId(1357);
        allLabelRecommendations.add(smithLabelRecommendation);
        allLabelRecommendations.add(LabelRecommendation);

        allLabelRecommendationsJson = mapper.writeValueAsString(allLabelRecommendations);
    }

    @Test
    public void shouldCreateNewLabelRecommendationOnPostRequest() throws Exception {
        LabelRecommendation inputLabelRecommendation = new LabelRecommendation(1,1,true);
        inputLabelRecommendation.setId(1357);
        String inputJson = mapper.writeValueAsString(inputLabelRecommendation);

        doReturn(smithLabelRecommendation).when(repo).save(inputLabelRecommendation);

        mockMvc.perform(
                        post("/labelRecommendation")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(smithJson));

    }

    @Test
    public void shouldReturnLabelRecommendationById() throws Exception {
        doReturn(Optional.of(smithLabelRecommendation)).when(repo).findById(9999);

         mockMvc.perform(
                        get("/labelRecommendation/9999"))
                .andExpect(status().isOk())
                .andExpect((content().json(smithJson))
                );
    }

    @Test
    public void shouldThrowBadExceptionLabelRecommendationId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(1234);

        mockMvc.perform(
                        get("/labelRecommendation/1234"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllLabelRecommendations() throws Exception {
        doReturn(allLabelRecommendations).when(repo).findAll();

        mockMvc.perform(
                        get("/labelRecommendation"))
                .andExpect(status().isOk())
                .andExpect(content().json(allLabelRecommendationsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation();
        outputLabelRecommendation.setLabel_id(1);
        outputLabelRecommendation.setUser_id(1);
        outputLabelRecommendation.setLiked(true);
        outputLabelRecommendation.setId(58);

        String outputJson = mapper.writeValueAsString(outputLabelRecommendation);

        mockMvc.perform(
                        put("/labelRecommendation/58")
                                .content(outputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/labelRecommendation/2")).andExpect(status().isNoContent());
    }

}