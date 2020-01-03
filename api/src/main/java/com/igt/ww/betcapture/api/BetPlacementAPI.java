package com.igt.ww.betcapture.api;

import java.util.*;
import javax.validation.constraints.*;

public interface BetPlacementAPI {

	BetInfo placeBet(@NotNull BetRequestInfo betRequestInfo);

	List<BetInfo> getAllBets();
}
