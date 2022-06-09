package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
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
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackRecommendationRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private TrackRecommendation smithTrackRecommendation;
    private String smithJson;
    private List<TrackRecommendation> allTrackRecommendations = new ArrayList<>();
    private String allTrackRecommendationsJson;

    @Before
    public void setup() throws Exception {
        smithTrackRecommendation = new TrackRecommendation(1,1,1,true);
        smithJson = mapper.writeValueAsString(smithTrackRecommendation);

        TrackRecommendation TrackRecommendation = new TrackRecommendation(1,1,true);
        TrackRecommendation.setId(1357);
        allTrackRecommendations.add(smithTrackRecommendation);
        allTrackRecommendations.add(TrackRecommendation);

        allTrackRecommendationsJson = mapper.writeValueAsString(allTrackRecommendations);
    }

    @Test
    public void shouldCreateNewTrackRecommendationOnPostRequest() throws Exception {
        TrackRecommendation inputTrackRecommendation = new TrackRecommendation(1,1,true);
        inputTrackRecommendation.setId(1357);
        String inputJson = mapper.writeValueAsString(inputTrackRecommendation);

        doReturn(smithTrackRecommendation).when(repo).save(inputTrackRecommendation);

        mockMvc.perform(
                        post("/trackRecommendation")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(smithJson));

    }

    @Test
    public void shouldReturnTrackRecommendationById() throws Exception {
        doReturn(Optional.of(smithTrackRecommendation)).when(repo).findById(9999);

        mockMvc.perform(
                        get("/trackRecommendation/9999"))
                .andExpect(status().isOk())
                .andExpect((content().json(smithJson))
                );
    }

    @Test
    public void shouldThrowBadExceptionTrackRecommendationId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(1234);

        mockMvc.perform(
                        get("/trackRecommendation/1234"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllTrackRecommendations() throws Exception {
        doReturn(allTrackRecommendations).when(repo).findAll();

        mockMvc.perform(
                        get("/trackRecommendation"))
                .andExpect(status().isOk())
                .andExpect(content().json(allTrackRecommendationsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        TrackRecommendation outputTrackRecommendation = new TrackRecommendation();
        outputTrackRecommendation.setTrack_id(1);
        outputTrackRecommendation.setUser_id(1);
        outputTrackRecommendation.setLiked(true);
        outputTrackRecommendation.setId(58);

        String outputJson = mapper.writeValueAsString(outputTrackRecommendation);

        mockMvc.perform(
                        put("/trackRecommendation/58")
                                .content(outputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/trackRecommendation/2")).andExpect(status().isNoContent());
    }
}