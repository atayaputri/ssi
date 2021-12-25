package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBTYPS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBTYPS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBTYPSRepository extends JpaRepository<TBTYPS, String> {}
