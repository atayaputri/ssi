package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TABFEE;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TABFEE entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TABFEERepository extends JpaRepository<TABFEE, Long> {}
