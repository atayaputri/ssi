package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.MFHDR;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MFHDR entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MFHDRRepository extends JpaRepository<MFHDR, Long> {}
