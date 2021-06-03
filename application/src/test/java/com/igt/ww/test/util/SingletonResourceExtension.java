package com.igt.ww.test.util;

import java.util.function.*;

import org.junit.jupiter.api.extension.*;

public class SingletonResourceExtension<T> implements TestInstancePostProcessor {

	private final Supplier<T> factory;
	private T singleton;

	public SingletonResourceExtension(Supplier<T> factory) {
		this.factory = factory;
	}

	public synchronized T getSingleton() {
		if (singleton == null)
			singleton = factory.get();
		return singleton;
	}

	private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(SingletonResourceExtension.class);

	@Override public void postProcessTestInstance(Object o, ExtensionContext context) {
		T singleton = getSingleton();
		if (singleton instanceof AutoCloseable) {
			var store = context.getRoot().getStore(NAMESPACE);
			store.getOrComputeIfAbsent(singleton.getClass(), k -> new ClosableSingleton<>((AutoCloseable)singleton));
		}
	}

	static class ClosableSingleton<T extends AutoCloseable> implements ExtensionContext.Store.CloseableResource {

		private final T singleton;

		ClosableSingleton(T singleton) {
			this.singleton = singleton;
		}

		@Override public void close() throws Exception {
			singleton.close();
		}
	}
}