package com.ipublic.ntipa.facerecognizer.repository;

import com.ipublic.ntipa.facerecognizer.domain.Face;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Face entity.
 */
public interface FaceRepository extends MongoRepository<Face, String> {

	
}
