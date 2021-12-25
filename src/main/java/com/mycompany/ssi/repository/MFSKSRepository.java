package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.MFSKS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MFSKS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFSKSRepository extends JpaRepository<MFSKS, Long> {}
