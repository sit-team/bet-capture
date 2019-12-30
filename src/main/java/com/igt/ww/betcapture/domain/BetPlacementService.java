package com.igt.ww.betcapture.domain;

import java.util.*;

public interface BetPlacementService {

    Bet placeBet(Bet bet);

    List<Bet> getAllBets();
}
