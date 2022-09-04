package com.blog.markdown.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.blog.markdown.entity.PIC;

public interface PICDao extends JpaRepository<PIC, Integer>, JpaSpecificationExecutor<PIC> {

}