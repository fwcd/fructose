package com.fredrikw.fructose.annotation;

/**
 * Marks something as "work-in-progess".
 * 
 * @author Fredrik
 *
 */
public @interface WIP {
	/**
	 * Whether the annotated element is (already) suited for production usage
	 */
	boolean usable() default false;
}
