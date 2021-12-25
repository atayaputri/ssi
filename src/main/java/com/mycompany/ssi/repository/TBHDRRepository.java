package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBHDR;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBHDR entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBHDRRepository extends JpaRepository<TBHDR, Long> {}
