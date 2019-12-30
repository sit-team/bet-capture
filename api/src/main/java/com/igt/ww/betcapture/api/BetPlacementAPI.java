package com.igt.ww.betcapture.api;

import java.util.*;

public interface BetPlacementAPI {

	BetInfo placeBet(BetRequestInfo betRequestInfo);

	List<BetInfo> getAllBets();
}
