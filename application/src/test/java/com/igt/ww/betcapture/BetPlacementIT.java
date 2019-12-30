package com.igt.ww.betcapture;

import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.*;

import org.assertj.core.api.*;
import org.junit.jupiter.api.*;
import org.mockito.invocation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.boot.test.web.client.*;
import org.springframework.core.*;
import org.springframework.http.*;

import com.igt.ww.betcapture.domain.*;
import com.igt.ww.betcapture.service.*;
import com.igt.ww.builder.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Index.atIndex;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class BetPlacementIT {

    private static final String BET_PLACE_URL = "/bet/place";
    private static final String RETRIEVE_BETS_URL = "/bet/all";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean(name = "betRepository")
    private BetRepository betRepository;

    @MockBean(name = "betRepositoryImpl")
    private BetRepositoryCustom betRepositoryCustom;

    private static final AtomicLong betId = new AtomicLong();

    @Test
    void singleBetWillBeSuccessfullyPlaced() {
        when(betRepository.save(any(Bet.class))).then(BetPlacementIT::saveBet);

        BetRequestInfo betRequestInfo = makeSingleBetRequest();
        ResponseEntity<BetInfo> betInfoResponse = restTemplate.postForEntity(BET_PLACE_URL, betRequestInfo, BetInfo.class);

        assertThat(betInfoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(betInfoResponse.getBody()).isInstanceOf(BetInfo.class);
        BetInfo betInfo = betInfoResponse.getBody();
        assertThat(betInfo.getId()).isEqualTo(1L);
        assertThat(betInfo.getMaxReturn()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void multipleBetWillBeSuccessfullyPlaced() {
        when(betRepository.save(any(Bet.class))).then(invocation -> {
            Bet argument = (Bet) invocation.getArguments()[0];
            argument.setId(2L);
            return argument;
        });

        BetRequestInfo betRequestInfo = makeMultipleBetRequest();
        ResponseEntity<BetInfo> betInfoResponse = restTemplate.postForEntity(BET_PLACE_URL, betRequestInfo, BetInfo.class);

        assertThat(betInfoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(betInfoResponse.getBody()).isInstanceOf(BetInfo.class);
        BetInfo betInfo = betInfoResponse.getBody();
        assertThat(betInfo.getId()).isEqualTo(2L);
        assertThat(betInfo.getMaxReturn()).isEqualTo(new BigDecimal(100));
    }

    @Test
    void allBetsWillBeRetrieved() {
        Bet bet1 = BetBuilder.aBet().withId(1L).withState(BetState.OPEN).build();
        Bet bet2 = BetBuilder.aBet().withId(2L).withState(BetState.CLOSED).build();
        when(betRepository.findAll()).thenReturn(List.of(bet1, bet2));

        ResponseEntity<List<BetInfo>> betInfosResponse = restTemplate.exchange(RETRIEVE_BETS_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<BetInfo>>() {});

        assertThat(betInfosResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<BetInfo> betInfos = betInfosResponse.getBody();
        assertThat(betInfos.size()).isEqualTo(2);
        assertThat(betInfos)
                .has(new Condition<>(betInfo -> betInfo.getId() == 1L, "First bet has ID = 1"), atIndex(0))
                .has(new Condition<>(betInfo -> betInfo.getBetState().equals(BetState.OPEN.name()), "First bet has state = OPEN"), atIndex(0))
                .has(new Condition<>(betInfo -> betInfo.getId() == 2L, "Second bet has ID = 2"), atIndex(1))
                .has(new Condition<>(betInfo -> betInfo.getBetState().equals(BetState.CLOSED.name()), "Second bet has state = CLOSED"), atIndex(1));
    }

    @Test
    void emptyBetRequestWillProduceBadRequestResponse() {
        BetRequestInfo betRequestInfo = new BetRequestInfo();
        ResponseEntity<ErrorInfo> betInfoResponse = restTemplate.postForEntity(BET_PLACE_URL, betRequestInfo, ErrorInfo.class);

        assertThat(betInfoResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorInfo errorInfo = betInfoResponse.getBody();
        assertThat(errorInfo).isInstanceOf(ErrorInfo.class);
        assertThat(errorInfo.getMessage()).isNotNull().isNotBlank();
    }

    private BetRequestInfo makeSingleBetRequest() {
        return BetRequestInfoBuilder
                .aBetRequestInfo()
                .withStake(BigDecimal.ONE)
                .withExternalId("Bet1")
                .withBetLegInfos(Collections.singletonList(
                        BetLegInfoBuilder.aBetLegInfo()
                                .withEventId(1L)
                                .withMarketId(1L)
                                .withSelectionId(1L)
                                .withPrice(BigDecimal.TEN)
                )).build();
    }

    private BetRequestInfo makeMultipleBetRequest() {
        return BetRequestInfoBuilder
                .aBetRequestInfo()
                .withStake(BigDecimal.ONE)
                .withExternalId("Bet1")
                .withBetLegInfos(List.of(
                        BetLegInfoBuilder.aBetLegInfo()
                                .withEventId(1L)
                                .withMarketId(1L)
                                .withSelectionId(1L)
                                .withPrice(BigDecimal.TEN),
                        BetLegInfoBuilder.aBetLegInfo()
                                .withEventId(2L)
                                .withMarketId(2L)
                                .withSelectionId(2L)
                                .withPrice(BigDecimal.TEN)
                )).build();
    }


    private static Object saveBet(InvocationOnMock invocation) {
        Bet argument = (Bet) invocation.getArguments()[0];
        argument.setId(betId.incrementAndGet());
        return argument;
    }
}
