package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBPROV;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBPROV entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBPROVRepository extends JpaRepository<TBPROV, String> {}
