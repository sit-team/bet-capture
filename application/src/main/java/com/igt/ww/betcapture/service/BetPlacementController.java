package com.igt.ww.betcapture.service;

import java.util.*;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.igt.ww.betcapture.api.*;
import com.igt.ww.betcapture.domain.*;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/v1/bet")
public class BetPlacementController implements BetPlacementAPI {

    private static final Logger LOG = LoggerFactory.getLogger(BetPlacementController.class);

    private BetPlacementService betPlacementService;

    public BetPlacementController(BetPlacementService betPlacementService) {
        this.betPlacementService = betPlacementService;
    }

    @PostMapping(value = "/place", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override public BetInfo placeBet(@RequestBody BetRequestInfo betRequestInfo) {
        LOG.trace("placeBet({})", betRequestInfo);
        validateBetRequest(betRequestInfo);
        var bet = betPlacementService.placeBet(BetAssembler.toBet(betRequestInfo));
        return BetAssembler.toBetInfo(bet);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override public List<BetInfo> getAllBets() {
        LOG.trace("getAllBets");
        return betPlacementService.getAllBets().stream().map(BetAssembler::toBetInfo).collect(toUnmodifiableList());
    }

    private void validateBetRequest(BetRequestInfo betRequestInfo) {
        if (betRequestInfo.getStake() == null)
            throw new InvalidBetRequestException("Bet request stake is not populated!");
        if (betRequestInfo.getBetLegInfos().isEmpty())
            throw new InvalidBetRequestException("Bet request legs are not populated!");
    }
}
