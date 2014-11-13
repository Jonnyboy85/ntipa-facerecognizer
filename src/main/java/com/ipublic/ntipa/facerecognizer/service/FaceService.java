package com.ipublic.ntipa.facerecognizer.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipublic.ntipa.facerecognizer.domain.Face;
import com.ipublic.ntipa.facerecognizer.repository.FaceRepository;

@Service
public class FaceService {

	private final Logger log = LoggerFactory.getLogger(FaceService.class);

	@Inject
	private FaceRepository faceRepository;

	@Inject
	private MongoTemplate mongoTemplate;

	
	@Inject
	private FaceRecognizerService faceRecognizerService;
	
	
	@Transactional
	public Face save(Face face) {
		log.debug("face:" + face);
		faceRepository.save(face);
		if (face != null && face.getCount() == null) {
			Query query = new Query(Criteria.where("_id").is(face.getId()));
			Update update = new Update().inc("count", 1);
			face = mongoTemplate.findAndModify(query, update, Face.class);
		}
		
		log.debug("face:" + face);
		
		faceRecognizerService.train(face);
		return face;

	}
}
