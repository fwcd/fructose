package fwcd.fructose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import fwcd.fructose.structs.DoubleList;

/**
 * Represents a weighted list of items from
 * which an item may be picked stochastically.
 * 
 * <p>NOTE that this implementation is NOT thread-safe.</p>
 * 
 * @author Fredrik
 *
 * @param <E> - The item type
 */
public class Distribution<E> implements BiIterable<E, Double> {
	private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
	private final List<E> items = new ArrayList<>();
	private DoubleList probabilities = new DoubleList();
	private double total = 0;
	
	public static enum Normalizer {
		NONE, NORMALIZE, SOFTMAX, SCALED_SOFTMAX;
	}
	
	public Distribution() {
		
	}
	
	public Distribution(Map<? extends E, ? extends Number> distribution) {
		addAll(distribution);
	}
	
	@Deprecated
	public Distribution(Map<E, Double> distribution, boolean softmaxed) {
		addAll(distribution, softmaxed);
	}
	
	public Distribution(Map<E, Double> distribution, Normalizer normalizer) {
		addAll(distribution, normalizer);
	}
	
	public void applySoftmax() {
		DoubleList exponentiated = probabilities.map(Math::exp);
		double sum = exponentiated.sum();
		exponentiated.mapInPlace(v -> v / sum);
		probabilities = exponentiated;
		total = 1;
	}
	
	public void addAll(Map<? extends E, ? extends Number> distribution) {
		for (E key : distribution.keySet()) {
			add(key, distribution.get(key).doubleValue());
		}
	}
	
	@Deprecated
	public void addAll(Map<E, Double> distribution, boolean softmaxed) {
		addAll(distribution, Normalizer.SOFTMAX);
	}
	
	public void addAll(Map<E, Double> distribution, Normalizer normalizer) {
		switch (normalizer) {
		case SCALED_SOFTMAX:
			double max = distribution.values().stream()
					.mapToDouble(Double::doubleValue)
					.max().orElse(1);
			distribution.entrySet().forEach(e -> e.setValue(e.getValue() / max));
			//$FALL-THROUGH$
		case SOFTMAX:
			Map<E, Double> softmaxed = new HashMap<>(distribution);
			
			double expSum = 0;
			Set<Entry<E, Double>> softmaxedEntries = softmaxed.entrySet();
			for (Map.Entry<E, Double> entry : softmaxedEntries) {
				double exp = Math.exp(entry.getValue());
				expSum += exp;
				entry.setValue(exp);
			}
			for (Map.Entry<E, Double> entry : softmaxedEntries) {
				double exp = entry.getValue();
				if (exp == 0D) {
					entry.setValue(exp);
				} else {
					entry.setValue(exp / expSum);
				}
			}
			addAll(softmaxed);
			break;
		case NORMALIZE:
			Map<E, Double> normalized = new HashMap<>(distribution);
			
			double sum = normalized.values().stream()
					.mapToDouble(Double::doubleValue)
					.sum();
			normalized = normalized.entrySet().stream()
					.collect(Collectors.toMap(Entry::getKey, e -> e.getValue() / sum));
			addAll(normalized);
			
			break;
		case NONE:
			addAll(distribution);
			break;
		default:
			throw new IllegalArgumentException("Unsupported normalizer.");
		}
	}
	
	public Map<E, Double> asMap() {
		Map<E, Double> map = new HashMap<>();
		for (int i=0; i<items.size(); i++) {
			map.put(items.get(i), probabilities.get(i));
		}
		return map;
	}
	
	public double getProbability(E item) {
		return probabilities.get(items.indexOf(item));
	}
	
	public void add(E item, double probability) {
		if (probability < 0) {
			throw new IllegalArgumentException("Probability can't be smaller than zero: " + Double.toString(probability));
		} else if (!Double.isFinite(probability)) {
			throw new IllegalArgumentException("Probability has to be finite: " + Double.toString(probability));
		}
		
		items.add(item);
		probabilities.add(probability);
		total += probability;
	}
	
	public int pickIndexStochastically() {
		int resultingIndex = -1;
		double random = RANDOM.nextDouble() * total;
		
		for (int i=0; i<items.size(); i++) {
			random -= probabilities.get(i);
			
			if (random <= 0) {
				resultingIndex = i;
				break;
			}
		}
		
		return resultingIndex;
	}
	
	public E pickStochastically() {
		return items.get(pickIndexStochastically());
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		
		int i = 0;
		for (E item : items) {
			s.append(item).append(": ").append(probabilities.get(i)).append(", ");
			i++;
		}
		
		return s.delete(s.length() - 2, s.length()).append("}").toString();
	}

	@Override
	public BiIterator<E, Double> iterator() {
		return new BiIterator<E, Double>() {
			int i = 0;
			
			@Override
			public boolean hasNext() {
				return i < items.size();
			}

			@Override
			public Pair<E, Double> next() {
				Pair<E, Double> pair = new Pair<>(items.get(i), probabilities.get(i));
				i++;
				return pair;
			}
		};
	}

	@Override
	public void forEach(BiConsumer<? super E, ? super Double> action) {
		for (int i=0; i<items.size(); i++) {
			action.accept(items.get(i), probabilities.get(i));
		}
	}
}
