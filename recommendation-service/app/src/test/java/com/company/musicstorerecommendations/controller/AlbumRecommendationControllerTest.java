package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private AlbumRecommendation smithAlbumRecommendation;
    private String smithJson;
    private List<AlbumRecommendation> allAlbumRecommendations = new ArrayList<>();
    private String allAlbumRecommendationsJson;

    @Before
    public void setup() throws Exception {
        smithAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        smithJson = mapper.writeValueAsString(smithAlbumRecommendation);

        AlbumRecommendation AlbumRecommendation = new AlbumRecommendation(1,1,true);
        AlbumRecommendation.setId(1357);
        allAlbumRecommendations.add(smithAlbumRecommendation);
        allAlbumRecommendations.add(AlbumRecommendation);

        allAlbumRecommendationsJson = mapper.writeValueAsString(allAlbumRecommendations);
    }

    @Test
    public void shouldCreateNewAlbumRecommendationOnPostRequest() throws Exception {
        AlbumRecommendation inputAlbumRecommendation = new AlbumRecommendation(1,1,true);
        inputAlbumRecommendation.setId(1357);
        String inputJson = mapper.writeValueAsString(inputAlbumRecommendation);

        doReturn(smithAlbumRecommendation).when(repo).save(inputAlbumRecommendation);

        mockMvc.perform(
                        post("/albumRecommendation")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(smithJson));

    }

    @Test
    public void shouldReturnAlbumRecommendationById() throws Exception {
        doReturn(Optional.of(smithAlbumRecommendation)).when(repo).findById(9999);

         mockMvc.perform(
                        get("/albumRecommendation/9999"))
                .andExpect(status().isOk())
                .andExpect((content().json(smithJson))
                );
    }

    @Test
    public void shouldThrowBadExceptionAlbumRecommendationId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(1234);

        mockMvc.perform(
                        get("/albumRecommendation/1234"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllAlbumRecommendations() throws Exception {
        doReturn(allAlbumRecommendations).when(repo).findAll();

        mockMvc.perform(
                        get("/albumRecommendation"))
                .andExpect(status().isOk())
                .andExpect(content().json(allAlbumRecommendationsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        AlbumRecommendation outputAlbumRecommendation = new AlbumRecommendation();
        outputAlbumRecommendation.setAlbum_id(1);
        outputAlbumRecommendation.setUser_id(1);
        outputAlbumRecommendation.setLiked(true);
        outputAlbumRecommendation.setId(58);

        String outputJson = mapper.writeValueAsString(outputAlbumRecommendation);

        mockMvc.perform(
                        put("/albumRecommendation/58")
                                .content(outputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/albumRecommendation/2")).andExpect(status().isNoContent());
    }

}