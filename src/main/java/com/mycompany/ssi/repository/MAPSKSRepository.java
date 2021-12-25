package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.MAPSKS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MAPSKS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAPSKSRepository extends JpaRepository<MAPSKS, Long> {}
