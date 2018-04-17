package com.fwcd.fructose.ml.function;

import java.util.concurrent.ThreadLocalRandom;

public enum NNWeightInit implements WeightInit {
	XAVIER {
		@Override
		public float getWeight(int inputNeurons, int outputNeurons) {
			return (float) (ThreadLocalRandom.current().nextGaussian() * Math.sqrt(2D / (inputNeurons + outputNeurons)));
		}
	},
	ZERO {
		@Override
		public float getWeight(int inputNeurons, int outputNeurons) {
			return 0;
		}
	},
	UNIFORM {
		@Override
		public float getWeight(int inputNeurons, int outputNeurons) {
			float a = 1 / (float) Math.sqrt(inputNeurons);
			return (ThreadLocalRandom.current().nextFloat() * 2 * a) - a;
		}
	},
	NORMALIZED {
		@Override
		public float getWeight(int inputNeurons, int outputNeurons) {
			return ThreadLocalRandom.current().nextFloat();
		}
	}
}
