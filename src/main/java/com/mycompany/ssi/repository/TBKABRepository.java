package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBKAB;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBKAB entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBKABRepository extends JpaRepository<TBKAB, String> {}
