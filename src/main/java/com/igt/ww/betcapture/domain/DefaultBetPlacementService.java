package com.igt.ww.betcapture.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultBetPlacementService implements BetPlacementService {

    private BetRepository betRepository;

    public DefaultBetPlacementService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    @Transactional
    public Bet placeBet(Bet bet) {
        bet.calculateMaxReturn();
        bet.setTimestamp(LocalDateTime.now());
        bet.setState(BetState.OPEN);
        return betRepository.save(bet);
    }

    @Override
    public List<Bet> getAllBets() {
        return betRepository.findAll();
    }
}
