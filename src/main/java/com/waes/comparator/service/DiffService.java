package com.waes.comparator.service;

import java.util.Arrays;

import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waes.comparator.entity.Document;
import com.waes.comparator.enums.Side;
import com.waes.comparator.repository.DocumentRepository;

@Service
public class DiffService {

	@Autowired
	public DocumentRepository repository;

	private static final Logger LOG = LoggerFactory.getLogger(DiffService.class);
	
	/**
	 * 
	 * Save the object
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param id unique identifier of the document
	 * @param data
	 * @param side
	 * @return
	 * @throws Exception
	 * Document
	 */
	public Document save(Long id, String data, Side side) throws Exception {
		Document document = null;
		if(validate(id, data)){
			document = repository.findById(id);
			if (document == null) {
				document = new Document();
				document.setId(id);
			}
	
			if(Side.LEFT.equals(side)) {
				document.setLeft(data);
			} else if(Side.RIGHT.equals(side)) {
				document.setRight(data);
			}else{
				LOG.warn("Invalid side sent.");
			}
			document = repository.save(document);
		}
		return document;
	}
	
	/**
	 * 
	 * Checks the data for ensure the document will be created correctly.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param id of the document
	 * @param data to be saved.
	 * @throws Exception
	 * void
	 */
	public boolean validate(Long id, String data) throws ValidationException {
		boolean isValid = true;
		LOG.trace("Entering validate(id={}, data={})", id, data);
		
		LOG.debug("Validating request for id '{}'", id);
		if (StringUtils.isEmpty(data)) {
			LOG.warn("Json data is blank or null");
			isValid = false;
		}
		LOG.trace("Leaving validate(id, data)={}", isValid);
		return isValid;
	}
	
	/**
	 * Do the core validation in order to compare Jsons and return its results
	 * 
	 * @param id
	 *            is used by repository to find a object
	 * @return a string with comparison results
	 */
	public String validateBase64Data(Long id) {
		LOG.trace("Entering validateBase64Data(id={})", id);
		LOG.debug("Will try to find the document by for id '{}'", id);
		
		Document document = repository.findById(id);
		if (document == null) {
			return "No data found";
		}

		LOG.debug("Document found. Will check the base64 data on both sides for id '{}'", id);
		if (!StringUtils.isNotBlank(document.getLeft()) || !StringUtils.isNotBlank(document.getRight())) {
			return "Base64 data missing";
		}
		byte[] bytesLeft = document.getLeft().getBytes();
		byte[] bytesRight = document.getRight().getBytes();

		boolean blnResult = Arrays.equals(bytesLeft, bytesRight);

		String offsets = "";

		if (blnResult) {
			return "Base64 data are equal";
		} else if (bytesLeft.length != bytesRight.length) {
			return "Base64 data have not same size.";
		} else {
			byte different = 0;
			for (int index = 0; index < bytesLeft.length; index++) {
				different = (byte) (bytesLeft[index] ^ bytesRight[index]);
				if (different != 0) {
					offsets = offsets + " " + index;
				}
			}
		}
		return "Base64 data got the same size, but their offsets are different:" + offsets;
	}

}
