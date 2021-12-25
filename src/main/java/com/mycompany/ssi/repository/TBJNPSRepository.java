package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBJNPS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBJNPS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBJNPSRepository extends JpaRepository<TBJNPS, String> {}
