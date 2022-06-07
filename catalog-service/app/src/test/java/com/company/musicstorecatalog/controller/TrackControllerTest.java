package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Track smithTrack;
    private String smithJson;
    private List<Track> allTracks = new ArrayList<>();
    private String allTracksJson;

    @Before
    public void setup() throws Exception {
        smithTrack = new Track(1,1,"Lose Yourself", 3);
        smithJson = mapper.writeValueAsString(smithTrack);

        Track Track = new Track(1,"Lose Yourself", 3);
        Track.setId(1357);
        allTracks.add(smithTrack);
        allTracks.add(Track);

        allTracksJson = mapper.writeValueAsString(allTracks);
    }

    @Test
    public void shouldCreateNewTrackOnPostRequest() throws Exception {
        Track inputTrack = new Track(1,"Lose Yourself", 3);
        inputTrack.setId(1357);
        String inputJson = mapper.writeValueAsString(inputTrack);

        doReturn(smithTrack).when(repo).save(inputTrack);

        mockMvc.perform(
                        post("/track")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(smithJson));

    }

    @Test
    public void shouldReturnTrackById() throws Exception {
        doReturn(Optional.of(smithTrack)).when(repo).findById(9999);

        ResultActions result = mockMvc.perform(
                        get("/track/9999"))
                .andExpect(status().isOk())
                .andExpect((content().json(smithJson))
                );
    }

    @Test
    public void shouldThrowBadExceptionTrackId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(1234);

        mockMvc.perform(
                        get("/track/1234"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllTracks() throws Exception {
        doReturn(allTracks).when(repo).findAll();

        mockMvc.perform(
                        get("/track"))
                .andExpect(status().isOk())
                .andExpect(content().json(allTracksJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        Track outputTrack = new Track();
        outputTrack.setAlbum_id(1);
        outputTrack.setTitle("Lose Yourself");
        outputTrack.setId(58);

        String outputJson = mapper.writeValueAsString(outputTrack);

        mockMvc.perform(
                        put("/track/58")
                                .content(outputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/track/2")).andExpect(status().isNoContent());
    }
}