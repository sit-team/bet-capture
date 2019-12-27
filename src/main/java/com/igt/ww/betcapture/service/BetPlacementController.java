package com.igt.ww.betcapture.service;

import com.igt.ww.betcapture.core.BetPlacementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public BetInfo placeBet(@RequestBody BetRequestInfo betRequestInfo) {
        LOG.trace("placeBet({})", betRequestInfo);
        validateBetRequest(betRequestInfo);
        var bet = betPlacementService.placeBet(BetAssembler.toBet(betRequestInfo));
        return BetAssembler.toBetInfo(bet);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BetInfo> getAllBets() {
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
