package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBPART;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBPART entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBPARTRepository extends JpaRepository<TBPART, String> {}
