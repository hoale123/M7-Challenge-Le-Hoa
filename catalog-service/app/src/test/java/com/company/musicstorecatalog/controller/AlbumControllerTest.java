package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Album smithAlbum;
    private String smithJson;
    private List<Album> allAlbums = new ArrayList<>();
    private String allAlbumsJson;

    @Before
    public void setup() throws Exception {
        smithAlbum = new Album(1,"Hello", 1, LocalDate.of(2000,6,6), 1, 9);
//        smithAlbum.setId(9999);
        smithJson = mapper.writeValueAsString(smithAlbum);

        Album album = new Album("Bye", 1, LocalDate.of(2000,10,6), 1, 9);
        album.setId(1357);
        allAlbums.add(smithAlbum);
        allAlbums.add(album);

        allAlbumsJson = mapper.writeValueAsString(allAlbums);
    }

    @Test
    public void shouldCreateNewAlbumOnPostRequest() throws Exception {
        Album inputAlbum = new Album("Bye", 1, LocalDate.of(2000,6,6), 1, 9);
        inputAlbum.setId(1357);
        String inputJson = mapper.writeValueAsString(inputAlbum);

        doReturn(smithAlbum).when(repo).save(inputAlbum);

        mockMvc.perform(
                        post("/album")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(smithJson));

    }

    @Test
    public void shouldReturnAlbumById() throws Exception {
        doReturn(Optional.of(smithAlbum)).when(repo).findById(9999);

        ResultActions result = mockMvc.perform(
                        get("/album/9999"))
                .andExpect(status().isOk())
                .andExpect((content().json(smithJson))
                );
    }

    @Test
    public void shouldThrowBadExceptionAlbumId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(1234);

        mockMvc.perform(
                        get("/album/1234"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllAlbums() throws Exception {
        doReturn(allAlbums).when(repo).findAll();

        mockMvc.perform(
                        get("/album"))
                .andExpect(status().isOk())
                .andExpect(content().json(allAlbumsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        Album outputAlbum = new Album();
        outputAlbum.setArtist_id(1);
        outputAlbum.setListPrice(1);
        outputAlbum.setTitle("Dr. Strange");
        outputAlbum.setReleaseDate(LocalDate.of(2006,2,1));
        outputAlbum.setId(58);

        String outputJson = mapper.writeValueAsString(outputAlbum);

        mockMvc.perform(
                        put("/album/58")
                                .content(outputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/album/2")).andExpect(status().isNoContent());
    }
}