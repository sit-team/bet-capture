package com.igt.ww.betcapture.client;

import java.util.*;

import com.igt.ww.betcapture.api.*;
import feign.*;

public interface BetPlacementClient extends BetPlacementAPI {

	@RequestLine("POST /bet/place")
	@Headers("Content-Type: application/json")
	@Override BetInfo placeBet(BetRequestInfo betRequestInfo);

	@RequestLine("GET /bet/all")
	@Override List<BetInfo> getAllBets();
}
