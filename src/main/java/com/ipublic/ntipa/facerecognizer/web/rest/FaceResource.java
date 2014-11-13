package com.ipublic.ntipa.facerecognizer.web.rest;



import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.ipublic.ntipa.facerecognizer.domain.Face;
import com.ipublic.ntipa.facerecognizer.repository.FaceRepository;

/**
 * REST controller for managing Face.
 */
@RestController
@RequestMapping("/app")
public class FaceResource {

    private final Logger log = LoggerFactory.getLogger(FaceResource.class);

    @Inject
    private FaceRepository faceRepository;

    /**
     * POST  /rest/faces -> Create a new face.
     */
    @RequestMapping(value = "/rest/faces",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Face face) {
        log.debug("REST request to save Face : {}", face);
        faceRepository.save(face);
        
        try
        {
	        String fotoCorrente = face.getPhoto();
	        fotoCorrente = fotoCorrente.substring(22); 
	        byte [] imgByteArray =  Base64.decode(fotoCorrente.getBytes()); 
	        
	        InputStream in =  new  ByteArrayInputStream( imgByteArray ); 
	        BufferedImage bufferedImage =  ImageIO.read( in ); 
	        ImageIO.write ( bufferedImage , "png", new  File("fotocache/"+face.getId()+".png"));
	        
        }catch ( Exception ex ){  
            ex.printStackTrace(); 
        }
    }

    /**
     * GET  /rest/faces -> get all the faces.
     */
    @RequestMapping(value = "/rest/faces",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Face> getAll() {
        log.debug("REST request to get all Faces");
        return faceRepository.findAll();
    }

    /**
     * GET  /rest/faces/:id -> get the "id" face.
     */
    @RequestMapping(value = "/rest/faces/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Face> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get Face : {}", id);
        Face face = faceRepository.findOne(id);
        if (face == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(face, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/faces/:id -> delete the "id" face.
     */
    @RequestMapping(value = "/rest/faces/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Face : {}", id);
        faceRepository.delete(id);
    }
}
