package fwcd.fructose;

/**
 * Implies that the implementor can be "copied" in some way.
 * 
 * @author Fredrik
 *
 * @param <S> - The subclass/implementor
 */
public interface Copyable<S extends Copyable<?>> {
	/**
	 * <p>A "copy" of this object. The precise meaning
	 * (e.g. whether it is a deep or shallow copy)
	 * may depend on the implementing class. Similar
	 * to Object.clone() the general contract is that</p>
	 * 
	 * <p>{@code this != copy && equals(copy)}</p>
	 * 
	 * <p>Contrastingly this method is designed to be
	 * much easier and safer to use than Object.clone() though,
	 * e.g. by not using checked exceptions, not relying on casts and enforcing
	 * public access to copy(). This method should always
	 * be "allowed" to be called and not pose any risk
	 * of throwing unexpected exceptions to the caller.</p>
	 * 
	 * @return The copy
	 */
	S copy();
}
