package com.igt.ww.betcapture;

import com.igt.ww.betcapture.domain.Bet;
import com.igt.ww.betcapture.domain.BetRepository;
import com.igt.ww.betcapture.domain.BetRepositoryCustom;
import com.igt.ww.betcapture.domain.BetState;
import com.igt.ww.betcapture.service.BetInfo;
import com.igt.ww.betcapture.service.BetRequestInfo;
import com.igt.ww.betcapture.service.ErrorInfo;
import com.igt.ww.builder.BetBuilder;
import com.igt.ww.builder.BetLegInfoBuilder;
import com.igt.ww.builder.BetRequestInfoBuilder;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@SuppressWarnings("ConstantConditions")
@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = {
                "management.endpoint.health.enabled=false",
                "management.endpoints.web.exposure.include=",
                "spring.flyway.enabled=false",
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
class BetPlacementIT {

    private static final String BET_PLACE_URL = "/bet/place";
    private static final String RETRIEVE_BETS_URL = "/bet/all";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean(name = "betRepository")
    private BetRepository betRepository;

    @SuppressWarnings("unused")
    @MockBean(name = "betRepositoryImpl")
    private BetRepositoryCustom betRepositoryCustom;

    @Test
    void singleBetWillBeSuccessfullyPlaced() {
        when(betRepository.save(argThat(
                argument -> argument.getState() == BetState.OPEN
                        && argument.getStake().equals(BigDecimal.ONE)
                        && argument.getLegs().size() == 1
                        && argument.getLegs().get(0).getPrice().equals(BigDecimal.TEN)
                        && argument.getMaxReturn().equals(BigDecimal.TEN))))
                .then(invocation -> {
                    Bet argument = (Bet) invocation.getArguments()[0];
                    argument.setId(1L);
                    return argument;
                });

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
        when(betRepository.save(argThat(
                argument -> argument.getState() == BetState.OPEN
                        && argument.getStake().equals(BigDecimal.ONE)
                        && argument.getLegs().size() == 2
                        && argument.getLegs().get(0).getPrice().equals(BigDecimal.TEN)
                        && argument.getLegs().get(1).getPrice().equals(BigDecimal.TEN)
                        && argument.getMaxReturn().equals(new BigDecimal(100)))))
                .then(invocation -> {
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

        ResponseEntity<List<BetInfo>> betInfosResponse = restTemplate.exchange(RETRIEVE_BETS_URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

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
}
