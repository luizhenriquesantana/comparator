package com.waes.comparator.repository.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waes.comparator.entity.Document;

public interface DocumentDAO extends JpaRepository<Document, Long> {

}
