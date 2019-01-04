package fwcd.fructose;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.function.Consumer;

import org.junit.Test;

public class ObservableTest {
	private int listenerCalls = 0;
	
	@Test
	public void testObservable() {
		listenerCalls = 0;
		
		Observable<String> str = new Observable<>("");
		Consumer<String> a = v -> listenerCalls++;
		Consumer<String> b = v -> listenerCalls++;
		
		str.listen(a);
		assertThat(listenerCalls, equalTo(0));
		
		str.listenAndFire(b);
		assertThat(listenerCalls, equalTo(1));
		
		str.set("test");
		assertThat(listenerCalls, equalTo(3));
		
		str.unlisten(a);
		str.unlisten(b);
		str.set("123");
		assertThat(listenerCalls, equalTo(3));
		assertThat(str.get(), equalTo("123"));
	}
	
	@Test
	public void testWeakObservers() {
		listenerCalls = 0;
		
		byte[] value = new byte[8];
		Observable<byte[]> obs = new Observable<>(value);
		assertThat(obs.get(), equalTo(value));
		
		obs.listenWeaklyAndFire(v -> listenerCalls++);
		assertThat(listenerCalls, equalTo(1));
		
		System.gc();
		assumeThat(obs.weakListenerCount(), equalTo(0));
	}
	
	@Test
	public void testObservableMap() {
		listenerCalls = 0;
		
		byte[] value = new byte[64];
		Observable<byte[]> obs = new Observable<>(value);
		testScopedDerivedObservable(value, obs);
		
		System.gc();
		assumeThat(obs.weakListenerCount(), equalTo(0));
		
		obs.set(new byte[16]);
		assertThat(listenerCalls, equalTo(1));
	}

	private void testScopedDerivedObservable(byte[] value, Observable<byte[]> obs) {
		ReadOnlyObservable<Integer> derived = obs.mapWeakly(it -> it.length);
		assertThat(obs.get(), equalTo(value));
		assertThat(derived.get(), equalTo(64));
		assertThat(obs.weakListenerCount(), equalTo(1));
		
		// Ensure that the weak observer is not collected too early
		System.gc();
		assumeThat(obs.weakListenerCount(), equalTo(1));
		
		derived.listen(v -> listenerCalls++);
		obs.set(new byte[32]);
		assertThat(listenerCalls, equalTo(1));
	}
}
