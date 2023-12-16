package com.azizbek.fancybackservice.repository;

import com.azizbek.fancybackservice.entity.Images;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Creator: Azizbek Avazov
 * Date: 13.08.2022
 * Time: 10:14
 */
@Repository
public interface ImagesRepo extends CrudRepository<Images, Long> {
}
