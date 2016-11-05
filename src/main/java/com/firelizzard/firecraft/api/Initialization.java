package com.firelizzard.firecraft.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Initialization {
	Class<?>[] after() default {};
	
	@Target({java.lang.annotation.ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Pre {
		Class<?>[] after() default {};
	}

	@Target({java.lang.annotation.ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface During {
		Class<?>[] after() default {};
	}

	@Target({java.lang.annotation.ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Post {
		Class<?>[] after() default {};
	}
}
