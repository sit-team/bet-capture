package com.igt.ww.betcapture.service;

import com.igt.ww.betcapture.api.*;
import com.igt.ww.betcapture.domain.Bet;
import com.igt.ww.betcapture.domain.BetLeg;
import com.igt.ww.betcapture.domain.BetState;

import java.util.stream.Collectors;

class BetAssembler {

    static BetInfo toBetInfo(Bet bet) {
        BetInfo betInfo = new BetInfo();
        betInfo.setId(bet.getId());
        betInfo.setExternalId(bet.getExternalId());
        betInfo.setBetState(bet.getState().name());
        betInfo.setStake(bet.getStake());
        betInfo.setMaxReturn(bet.getMaxReturn());
        betInfo.setTimestamp(bet.getTimestamp());
        betInfo.setBetLegInfos(bet.getLegs().stream()
                .map(BetAssembler::toBetLegInfo)
                .collect(Collectors.toList()));
        return betInfo;
    }

    private static BetLegInfo toBetLegInfo(BetLeg betLeg) {
        BetLegInfo betLegInfo = new BetLegInfo();
        betLegInfo.setLegIndex(betLeg.getLegIndex());
        betLegInfo.setEventId(betLeg.getEventId());
        betLegInfo.setMarketId(betLeg.getMarketId());
        betLegInfo.setSelectionId(betLeg.getSelectionId());
        betLegInfo.setPrice(betLeg.getPrice());
        return betLegInfo;
    }

    static Bet toBet(BetRequestInfo betRequestInfo) {
        Bet bet = new Bet();
        bet.setExternalId(betRequestInfo.getExternalId());
        bet.setStake(betRequestInfo.getStake());
        bet.setState(BetState.ATTEMPTED);
        var betLegInfos = betRequestInfo.getBetLegInfos();
        for (int i = 0; i < betLegInfos.size(); i++) {
            bet.addLeg(createBegLeg(betLegInfos.get(i), i));
        }
        return bet;
    }

    private static BetLeg createBegLeg(BetLegInfo betLegInfo, int i) {
        BetLeg betLeg = new BetLeg(i);
        betLeg.setEventId(betLegInfo.getEventId());
        betLeg.setMarketId(betLegInfo.getMarketId());
        betLeg.setSelectionId(betLegInfo.getSelectionId());
        betLeg.setPrice(betLegInfo.getPrice());
        return betLeg;
    }
}