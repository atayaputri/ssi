package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TABJTRX;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TABJTRX entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TABJTRXRepository extends JpaRepository<TABJTRX, String> {}
