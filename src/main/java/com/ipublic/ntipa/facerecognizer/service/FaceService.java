package com.ipublic.ntipa.facerecognizer.service;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipublic.ntipa.facerecognizer.domain.Face;
import com.ipublic.ntipa.facerecognizer.repository.FaceRepository;

@Service
@Transactional
public class FaceService {

	private final Logger log = LoggerFactory.getLogger(FaceService.class);

	@Inject
	private FaceRepository faceRepository;


	@Inject
	private FaceRecognizerService faceRecognizerService;

	
	@Transactional(readOnly=true)
	public Face verifica(Face faceDaVerificare) throws IOException {
		Integer count = faceRecognizerService.predict(faceDaVerificare);
		if(count == null )
			throw new RuntimeException("Faccia non trovata");
		return faceRepository.findByCount(count);
	}
	
	@Transactional
	public Face save(Face face) {
		log.debug("face:" + face);
		faceRepository.save(face);
		if (face != null && face.getCount() == null) {
			Long count = faceRepository.count();
			face.setCount(count.intValue());
		}

		faceRepository.save(face);
		log.debug("face:" + face);

		//faceRecognizerService.train(face);
		return face;

	}
}
