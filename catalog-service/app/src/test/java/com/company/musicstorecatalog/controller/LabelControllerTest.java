package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
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
@WebMvcTest(LabelController.class)
public class LabelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Label smithLabel;
    private String smithJson;
    private List<Label> allLabels = new ArrayList<>();
    private String allLabelsJson;

    @Before
    public void setup() throws Exception {
        smithLabel = new Label(1,"Sony Music Entertainment", "Sony.com");
        smithJson = mapper.writeValueAsString(smithLabel);

        Label label = new Label("Sony Music Entertainment", "Sony.com");
        label.setId(1357);
        allLabels.add(smithLabel);
        allLabels.add(label);

        allLabelsJson = mapper.writeValueAsString(allLabels);
    }

    @Test
    public void shouldCreateNewLabelOnPostRequest() throws Exception {
        Label inputLabel = new Label("Sony Music Entertainment", "Sony.com");
        inputLabel.setId(1357);
        String inputJson = mapper.writeValueAsString(inputLabel);

        doReturn(smithLabel).when(repo).save(inputLabel);

        mockMvc.perform(
                        post("/label")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(smithJson));

    }

    @Test
    public void shouldReturnLabelById() throws Exception {
        doReturn(Optional.of(smithLabel)).when(repo).findById(9999);

         mockMvc.perform(
                        get("/label/9999"))
                .andExpect(status().isOk())
                .andExpect((content().json(smithJson))
                );
    }

    @Test
    public void shouldThrowBadExceptionLabelId() throws Exception {
        doThrow(new BadIdException()).when(repo).findById(1234);

        mockMvc.perform(
                        get("/label/1234"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllLabels() throws Exception {
        doReturn(allLabels).when(repo).findAll();

        mockMvc.perform(
                        get("/label"))
                .andExpect(status().isOk())
                .andExpect(content().json(allLabelsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        Label outputLabel = new Label();
        outputLabel.setWebsite("Sony.com");
        outputLabel.setName("Sony Music Entertainment");
        outputLabel.setId(58);

        String outputJson = mapper.writeValueAsString(outputLabel);

        mockMvc.perform(
                        put("/label/58")
                                .content(outputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        mockMvc.perform(delete("/label/2")).andExpect(status().isNoContent());
    }
}