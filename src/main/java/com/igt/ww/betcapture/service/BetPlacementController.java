package com.igt.ww.betcapture.service;

import com.igt.ww.betcapture.core.BetPlacementService;
import com.igt.ww.betcapture.domain.Bet;
import com.igt.ww.betcapture.domain.BetLeg;
import com.igt.ww.betcapture.domain.BetState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@RequestMapping("/bet")
public class BetPlacementController {

    private static final Logger LOG = LoggerFactory.getLogger(BetPlacementController.class);

    private BetPlacementService betPlacementService;

    public BetPlacementController(BetPlacementService betPlacementService) {
        this.betPlacementService = betPlacementService;
    }

    @PostMapping(value = "/place", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> placeBet(@RequestBody BetRequestInfo betRequestInfo) {
        try {
            final boolean valid = validateBetRequest(betRequestInfo);
            if (!valid) {
                return ResponseEntity.badRequest().body(new ErrorInfo("Bet request is not populated!"));
            }
            var bet = betPlacementService.placeBet(toBet(betRequestInfo));
            return ResponseEntity.ok(toBetInfo(bet));
        } catch (Throwable e) {
            LOG.error("Error happened during bet placement for {}!", betRequestInfo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBets() {
        try {
            return ResponseEntity.ok(betPlacementService.getAllBets().stream().map(this::toBetInfo).collect(toUnmodifiableList()));
        } catch (Exception e) {
            LOG.error("Error while retrieving all bets!", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private BetInfo toBetInfo(Bet bet) {
        BetInfo betInfo = new BetInfo();
        betInfo.setId(bet.getId());
        betInfo.setExternalId(bet.getExternalId());
        betInfo.setBetState(bet.getState().name());
        betInfo.setStake(bet.getStake());
        betInfo.setMaxReturn(bet.getMaxReturn());
        betInfo.setTimestamp(bet.getTimestamp());
        betInfo.setBetLegInfos(bet.getLegs().stream()
                .map(this::toBetLegInfo)
                .collect(toList()));
        return betInfo;
    }

    private BetLegInfo toBetLegInfo(BetLeg betLeg) {
        BetLegInfo betLegInfo = new BetLegInfo();
        betLegInfo.setLegIndex(betLeg.getLegIndex());
        betLegInfo.setEventId(betLeg.getEventId());
        betLegInfo.setMarketId(betLeg.getMarketId());
        betLegInfo.setSelectionId(betLeg.getSelectionId());
        betLegInfo.setPrice(betLeg.getPrice());
        return betLegInfo;
    }

    private Bet toBet(BetRequestInfo betRequestInfo) {
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

    private BetLeg createBegLeg(BetLegInfo betLegInfo, int i) {
        BetLeg betLeg = new BetLeg(i);
        betLeg.setEventId(betLegInfo.getEventId());
        betLeg.setMarketId(betLegInfo.getMarketId());
        betLeg.setSelectionId(betLegInfo.getSelectionId());
        betLeg.setPrice(betLegInfo.getPrice());
        return betLeg;
    }

    private boolean validateBetRequest(BetRequestInfo betRequestInfo) {
        return betRequestInfo.getStake() != null && !betRequestInfo.getBetLegInfos().isEmpty();
    }
}
