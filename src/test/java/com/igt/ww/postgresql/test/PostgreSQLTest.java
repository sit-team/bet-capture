package com.igt.ww.postgresql.test;

import java.lang.annotation.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.test.context.*;

import static org.junit.jupiter.api.TestInstance.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@ExtendWith(PostgreSQLExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = {PostgreSQLDataSourceInitializer.class})
@TestInstance(Lifecycle.PER_CLASS)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostgreSQLTest {}
