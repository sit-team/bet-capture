CREATE TABLE bet (
	bet_id SERIAL PRIMARY KEY,
	external_id TEXT NOT NULL,
	state TEXT NOT NULL,
	stake NUMERIC NOT NULL,
	max_return NUMERIC NOT NULL,
	timestamp TIMESTAMP NOT NULL,
	version INTEGER
);

CREATE INDEX ON bet (external_id);

CREATE TABLE bet_leg (
	bet_id BIGINT NOT NULL REFERENCES bet (bet_id),
	leg_index INTEGER NOT NULL,
	event_id BIGINT NOT NULL,
	market_id BIGINT NOT NULL,
	selection_id BIGINT NOT NULL,
   price NUMERIC NOT NULL,
	PRIMARY KEY (bet_id, leg_index)
);
