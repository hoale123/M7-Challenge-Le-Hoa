package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private ArtistRecommendation smithArtistRecommendation;
    private String smithJson;
    private List<ArtistRecommendation> allArtistRecommendations = new ArrayList<>();
    private String allArtistRecommendationsJson;

    @Before
    public void setup() throws Exception {
        smithArtistRecommendation = new ArtistRecommendation(1,1,1,true);
        smithJson = mapper.writeValueAsString(smithArtistRecommendation);

        ArtistRecommendation ArtistRecommendation = new ArtistRecommendation(1,1,true);
        ArtistRecommendation.setId(1357);
        allArtistRecommendations.add(smithArtistRecommendation);
        allArtistRecommendations.add(ArtistRecommendation);

        allArtistRecommendationsJson = mapper.writeValueAsString(allArtistRecommendations);
    }

    @Test
    public void shouldCreateNewArtistRecommendationOnPostRequest() throws Exception {
        ArtistRecommendation inputArtistRecommendation = new ArtistRecommendation(1,1,true);
        inputArtistRecommendation.setId(1357);
        String inputJson = mapper.writeValueAsString(inputArtistRecommendation);

        doReturn(smithArtistRecommendation).when(repo).save(inputArtistRecommendation);

        mockMvc.perform(
                        post("/artistRecommendation")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(smithJson));

    }

    @Test
    public void shouldReturnArtistRecommendationById() throws Exception {
        doReturn(Optional.of(smithArtistRecommendation)).when(repo).findById(9999);

         mockMvc.perform(
                        get("/artistRecommendation/9999"))
                .andExpect(status().isOk())
                .andExpect((content().json(smithJson))
                );
    }

    @Test
    public void shouldThrowBadExceptionArtistRecommendationId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(1234);

        mockMvc.perform(
                        get("/artistRecommendation/1234"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllArtistRecommendations() throws Exception {
        doReturn(allArtistRecommendations).when(repo).findAll();

        mockMvc.perform(
                        get("/artistRecommendation"))
                .andExpect(status().isOk())
                .andExpect(content().json(allArtistRecommendationsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        ArtistRecommendation outputArtistRecommendation = new ArtistRecommendation();
        outputArtistRecommendation.setArtist_id(1);
        outputArtistRecommendation.setUser_id(1);
        outputArtistRecommendation.setLiked(true);
        outputArtistRecommendation.setId(58);

        String outputJson = mapper.writeValueAsString(outputArtistRecommendation);

        mockMvc.perform(
                        put("/artistRecommendation/58")
                                .content(outputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/artistRecommendation/2")).andExpect(status().isNoContent());
    }

}