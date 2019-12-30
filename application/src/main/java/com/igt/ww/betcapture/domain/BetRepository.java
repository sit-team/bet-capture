package com.igt.ww.betcapture.domain;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long>, BetRepositoryCustom {

	List<Bet> findAllByState(BetState state);
}
