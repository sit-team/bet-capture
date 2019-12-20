package com.igt.ww.betcapture.domain;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import static java.util.AbstractMap.*;
import static java.util.stream.Collectors.*;

@Repository
public class BetRepositoryImpl implements BetRepositoryCustom {

	@Autowired private JdbcTemplate jdbcTemplate;

	private static final String QUERY_BET_STATE_COUNTS = "SELECT state, count(*) AS count FROM bet GROUP BY state";

	@Override public SortedMap<BetState, Integer> findBetStateCounts() {
		return jdbcTemplate.query(
			QUERY_BET_STATE_COUNTS,
			(rs, i) -> new AbstractMap.SimpleEntry<>(BetState.valueOf(rs.getString("state")), rs.getInt("count"))
		).stream().collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue, (e1, e2) -> e1, TreeMap::new));
	}
}
