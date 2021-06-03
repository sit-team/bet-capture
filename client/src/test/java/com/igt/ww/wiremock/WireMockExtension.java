package com.igt.ww.wiremock;

import org.junit.jupiter.api.extension.*;

import com.github.tomakehurst.wiremock.*;
import com.github.tomakehurst.wiremock.client.*;
import com.github.tomakehurst.wiremock.core.*;

public class WireMockExtension implements TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback {

	private static WireMockServer server;

	public static synchronized WireMockServer getWireMockServer() {
		if (server == null) {
			server = new SharedWireMockServer();
			server.start();
		}
		return server;
	}

	public static int port() {
		return getWireMockServer().port();
	}

	private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(WireMockExtension.class);

	@Override public void postProcessTestInstance(Object o, ExtensionContext context) {
		var store = context.getRoot().getStore(NAMESPACE);
		store.getOrComputeIfAbsent("WireMockServer", k -> getWireMockServer());
	}

	@Override public void beforeEach(ExtensionContext extensionContext) {
		WireMock.configureFor(server.port());
	}

	@Override public void afterEach(ExtensionContext extensionContext) {
		WireMock.reset();
	}

	static class SharedWireMockServer extends WireMockServer implements ExtensionContext.Store.CloseableResource {


		public SharedWireMockServer() {
			super(WireMockConfiguration.options().dynamicPort());
		}

		@Override public void close() {
			stop();
		}
	}
}