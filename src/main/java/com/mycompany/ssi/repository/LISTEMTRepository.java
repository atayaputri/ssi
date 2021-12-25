package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.LISTEMT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LISTEMT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LISTEMTRepository extends JpaRepository<LISTEMT, Long> {}
