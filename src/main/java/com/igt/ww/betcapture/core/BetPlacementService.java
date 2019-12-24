package com.igt.ww.betcapture.core;

import com.igt.ww.betcapture.domain.Bet;

import java.util.List;

public interface BetPlacementService {

    Bet placeBet(Bet bet);

    List<Bet> getAllBets();
}
