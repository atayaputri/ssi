package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBCOMS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBCOMS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBCOMSRepository extends JpaRepository<TBCOMS, String> {}
