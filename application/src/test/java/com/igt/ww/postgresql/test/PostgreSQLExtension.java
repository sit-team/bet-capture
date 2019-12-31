package com.igt.ww.postgresql.test;

import org.junit.jupiter.api.extension.*;
import org.testcontainers.containers.*;

public class PostgreSQLExtension implements TestInstancePostProcessor {

	private static PostgreSQLContainer container;

	public static synchronized PostgreSQLContainer getContainer() {
		if (container == null) {
			container = new SharedPostgreSQLContainer("postgres");
			container.start();
		}
		return container;
	}

	private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(PostgreSQLExtension.class);

	@Override public void postProcessTestInstance(Object o, ExtensionContext context) {
		var store = context.getRoot().getStore(NAMESPACE);
		store.getOrComputeIfAbsent("PostgreSQLContainer", k -> getContainer());
	}

	static class SharedPostgreSQLContainer extends PostgreSQLContainer implements ExtensionContext.Store.CloseableResource {

		public SharedPostgreSQLContainer(String dockerImageName) {
			super(dockerImageName);
		}

		@Override public void close() {
			super.close();
		}
	}
}