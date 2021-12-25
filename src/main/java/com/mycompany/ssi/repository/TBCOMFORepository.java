package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBCOMFO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBCOMFO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBCOMFORepository extends JpaRepository<TBCOMFO, Long> {}
