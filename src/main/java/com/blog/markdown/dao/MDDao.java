package com.blog.markdown.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.blog.markdown.entity.MD;

public interface MDDao extends JpaRepository<MD, Integer>, JpaSpecificationExecutor<MD> {

}