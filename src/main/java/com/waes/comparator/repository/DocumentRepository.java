package com.waes.comparator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waes.comparator.entity.Document;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long>  {

	/**
	 * 
	 * Method will find the document by its ID.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param id
	 * @return
	 * Document
	 */
	Document findById(long id);
}