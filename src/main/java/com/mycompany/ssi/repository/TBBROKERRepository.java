package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBBROKER;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBBROKER entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBBROKERRepository extends JpaRepository<TBBROKER, String> {}
