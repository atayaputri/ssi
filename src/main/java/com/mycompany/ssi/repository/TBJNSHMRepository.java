package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBJNSHM;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBJNSHM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBJNSHMRepository extends JpaRepository<TBJNSHM, String> {}
