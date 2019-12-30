package com.igt.ww.betcapture.domain;

import java.util.*;

public interface BetRepositoryCustom {

	SortedMap<BetState, Integer> findBetStateCounts();
}
