package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.MFSHM;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MFSHM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFSHMRepository extends JpaRepository<MFSHM, Long> {}
