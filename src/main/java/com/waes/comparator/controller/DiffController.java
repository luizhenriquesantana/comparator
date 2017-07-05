package com.waes.comparator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waes.comparator.enums.Side;
import com.waes.comparator.service.DiffService;
import com.waes.comparator.to.JSONObject;

/**
 * 
 * Application Rest controller.  It maps the api endpoints and bring the results.
 *
 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
 * @version $Revision: 1.1 $
 */
@RestController
@RequestMapping("/v1/diff/{id}")
public class DiffController {

	private static final Logger LOG = LoggerFactory.getLogger(DiffController.class);
	
	@Autowired
	private DiffService service;
	
	/**
	 * 
	 * Post the left side value for comparison
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param id unique identifier. If posted some already existing value, it will be updated. 
	 * @param data string based base64 value for being updated. 
	 * @return a simple message for be showed on the response.
	 * @throws Exception
	 * String
	 */
	@RequestMapping(value = "/left", method = RequestMethod.POST, produces = "application/json")
	private String left(@PathVariable Long id, @RequestBody JSONObject data) throws Exception {
		LOG.trace("Entering left(id={}, data={})", id, data);
		
		LOG.debug("Setting '{}' side of the document with the value: '{}'", Side.LEFT, data);
		service.save(id, data.getData(), Side.LEFT);
		LOG.info("'{}' side of the document saved successfuly for id: '{}'", Side.LEFT, id);
		
		String message = buildJsonResponse("Document left-side saved successfuly");
		LOG.trace("Leaving left(id, data)={}", message);	
		return message;
	}

	/**
	 * 
	 * Post the right side value for comparison
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param id unique identifier. If posted some already existing value, it will be updated. 
	 * @param data string based base64 value for being updated. 
	 * @return a simple message for be showed on the response.
	 * @throws Exception
	 * String
	 */
	@RequestMapping(value = "/right", method = RequestMethod.POST, produces = "application/json")
	private String right(@PathVariable Long id, @RequestBody JSONObject data) throws Exception {
		LOG.trace("Entering right(id={}, data={})", id, data);
		
		LOG.debug("Setting '{}' side of the document with the value: '{}'", Side.RIGHT, data);
		service.save(id, data.getData(), Side.RIGHT);
		LOG.info("'{}' side of the document saved successfuly for document: '{}'", Side.RIGHT, id);
		
		String message = buildJsonResponse("Document right-side successfuly");
		LOG.trace("Leaving right(id, data)={}", message);	
		return message;
	}
	
	/**
	 * 
	 * Returns the comparison result between left and right sides of a document.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param id document identifier
	 * @return a message with the result.
	 * String
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	private String diff(@PathVariable Long id) {
		return buildJsonResponse(service.validateBase64Data(id));
	}
	
	/**
	 * 
	 * Just create a successful message.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param message to be included on the json response
	 * @return the successful message.
	 * String
	 */
	private String buildJsonResponse(String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"");
		sb.append("message");
		sb.append("\":");
		sb.append("\"");
		sb.append(message);
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}
}
