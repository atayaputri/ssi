package com.mycompany.ssi.repository;

import com.mycompany.ssi.domain.TBNEG;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TBNEG entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBNEGRepository extends JpaRepository<TBNEG, String> {}
