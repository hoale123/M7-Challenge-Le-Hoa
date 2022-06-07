package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
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

import java.time.LocalDate;
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
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Artist smithArtist;
    private String smithJson;
    private List<Artist> allArtists = new ArrayList<>();
    private String allArtistsJson;

    @Before
    public void setup() throws Exception {
        smithArtist = new Artist(1,"Hoa", "Hoa123", "Hoa123");
        smithJson = mapper.writeValueAsString(smithArtist);

        Artist artist = new Artist("Hoa", "Hoa123", "Hoa123");
        artist.setId(1357);
        allArtists.add(smithArtist);
        allArtists.add(artist);

        allArtistsJson = mapper.writeValueAsString(allArtists);
    }

    @Test
    public void shouldCreateNewArtistOnPostRequest() throws Exception {
        Artist inputArtist = new Artist("Hoa", "Hoa123", "Hoa123");
        inputArtist.setId(1357);
        String inputJson = mapper.writeValueAsString(inputArtist);

        doReturn(smithArtist).when(repo).save(inputArtist);

        mockMvc.perform(
                        post("/artist")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(smithJson));

    }

    @Test
    public void shouldReturnArtistById() throws Exception {
        doReturn(Optional.of(smithArtist)).when(repo).findById(9999);

        ResultActions result = mockMvc.perform(
                        get("/artist/9999"))
                .andExpect(status().isOk())
                .andExpect((content().json(smithJson))
                );
    }

    @Test
    public void shouldThrowBadExceptionArtistId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(1234);

        mockMvc.perform(
                        get("/artist/1234"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllArtists() throws Exception {
        doReturn(allArtists).when(repo).findAll();

        mockMvc.perform(
                        get("/artist"))
                .andExpect(status().isOk())
                .andExpect(content().json(allArtistsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        Artist outputArtist = new Artist();
        outputArtist.setInstagram("hoa123");
        outputArtist.setName("hoa");
        outputArtist.setTwitter("Dr. Strange");
        outputArtist.setId(58);

        String outputJson = mapper.writeValueAsString(outputArtist);

        mockMvc.perform(
                        put("/artist/58")
                                .content(outputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/artist/2")).andExpect(status().isNoContent());
    }

}