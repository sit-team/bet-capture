package com.igt.ww.unit;

import com.igt.ww.betcapture.domain.DefaultBetPlacementService;
import com.igt.ww.betcapture.domain.Bet;
import com.igt.ww.betcapture.domain.BetRepository;
import com.igt.ww.betcapture.domain.BetState;
import com.igt.ww.betcapture.builder.BetBuilder;
import org.assertj.core.data.Index;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DefaultBetPlacementServiceTest {

    @Mock
    private BetRepository betRepository;

    @InjectMocks
    private DefaultBetPlacementService betPlacementService;

    @Test
    void placeBet() {
        given(betRepository.save(any(Bet.class))).will(invocation -> {
            var bet = (Bet) invocation.getArguments()[0];
            bet.setId(1L);
            return bet;
        });

        Bet bet = BetBuilder.defaultBet().build();
        Bet resultBet = betPlacementService.placeBet(bet);

        assertThat(resultBet.getId()).isEqualTo(1L);
        assertThat(resultBet.getTimestamp()).isNotNull();
        assertThat(resultBet.getState()).isEqualTo(BetState.OPEN);
        assertThat(resultBet.getMaxReturn()).isCloseTo(new BigDecimal("2.0"), Offset.offset(new BigDecimal("0.01")));
    }

    @Test
    void getAllBets() {
        Bet bet1 = BetBuilder.aBet().withId(1L).build();
        Bet bet2 = BetBuilder.aBet().withId(2L).build();
        given(betRepository.findAll()).willReturn(List.of(bet1, bet2));

        List<Bet> allBets = betPlacementService.getAllBets();

        assertThat(allBets.size()).isEqualTo(2);
        assertThat(allBets).extracting(Bet::getId).contains(1L, Index.atIndex(0)).contains(2L, Index.atIndex(1));
    }
}
