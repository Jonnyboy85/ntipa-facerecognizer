package com.ipublic.ntipa.facerecognizer.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.ipublic.ntipa.facerecognizer.Application;
import com.ipublic.ntipa.facerecognizer.domain.Face;
import com.ipublic.ntipa.facerecognizer.repository.FaceRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FaceResource REST controller.
 *
 * @see FaceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FaceResourceTest {

    private static final String DEFAULT_LABEL = "SAMPLE_TEXT";
    private static final String UPDATED_LABEL = "UPDATED_TEXT";
    
    private static final String DEFAULT_PATH = "SAMPLE_TEXT";
    private static final String UPDATED_PATH = "UPDATED_TEXT";
    

   @Inject
   private FaceRepository faceRepository;

   private MockMvc restFaceMockMvc;

   private Face face;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FaceResource faceResource = new FaceResource();
        ReflectionTestUtils.setField(faceResource, "faceRepository", faceRepository);
        this.restFaceMockMvc = MockMvcBuilders.standaloneSetup(faceResource).build();
    }

    @Before
    public void initTest() {
        faceRepository.deleteAll();
        face = new Face();
        face.setLabel(DEFAULT_LABEL);
        face.setPath(DEFAULT_PATH);
    }

    @Test
    public void createFace() throws Exception {
        // Validate the database is empty
        assertThat(faceRepository.findAll()).hasSize(0);

        // Create the Face
        restFaceMockMvc.perform(post("/app/rest/faces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(face)))
                .andExpect(status().isOk());

        // Validate the Face in the database
        List<Face> faces = faceRepository.findAll();
        assertThat(faces).hasSize(1);
        Face testFace = faces.iterator().next();
        assertThat(testFace.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testFace.getPath()).isEqualTo(DEFAULT_PATH);;
    }

    @Test
    public void getAllFaces() throws Exception {
        // Initialize the database
        faceRepository.save(face);

        // Get all the faces
        restFaceMockMvc.perform(get("/app/rest/faces"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(face.getId()))
                .andExpect(jsonPath("$.[0].label").value(DEFAULT_LABEL.toString()))
                .andExpect(jsonPath("$.[0].path").value(DEFAULT_PATH.toString()));
    }

    @Test
    public void getFace() throws Exception {
        // Initialize the database
        faceRepository.save(face);

        // Get the face
        restFaceMockMvc.perform(get("/app/rest/faces/{id}", face.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(face.getId()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    public void getNonExistingFace() throws Exception {
        // Get the face
        restFaceMockMvc.perform(get("/app/rest/faces/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFace() throws Exception {
        // Initialize the database
        faceRepository.save(face);

        // Update the face
        face.setLabel(UPDATED_LABEL);
        face.setPath(UPDATED_PATH);
        restFaceMockMvc.perform(post("/app/rest/faces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(face)))
                .andExpect(status().isOk());

        // Validate the Face in the database
        List<Face> faces = faceRepository.findAll();
        assertThat(faces).hasSize(1);
        Face testFace = faces.iterator().next();
        assertThat(testFace.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testFace.getPath()).isEqualTo(UPDATED_PATH);;
    }

    @Test
    public void deleteFace() throws Exception {
        // Initialize the database
        faceRepository.save(face);

        // Get the face
        restFaceMockMvc.perform(delete("/app/rest/faces/{id}", face.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Face> faces = faceRepository.findAll();
        assertThat(faces).hasSize(0);
    }
}
