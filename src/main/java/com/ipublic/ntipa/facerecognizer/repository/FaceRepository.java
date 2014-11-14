package com.ipublic.ntipa.facerecognizer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ipublic.ntipa.facerecognizer.domain.Face;

/**
 * Spring Data MongoDB repository for the Face entity.
 */
public interface FaceRepository extends MongoRepository<Face, String> {

}
