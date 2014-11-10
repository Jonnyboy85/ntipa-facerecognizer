package com.ipublic.ntipa.facerecognizer.repository;

import com.ipublic.ntipa.facerecognizer.domain.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
