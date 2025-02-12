/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.classic.spi;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.spi.ContextAware;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows programmatic initialization and configuration of Logback. The
 * ServiceLoader is typically used to instantiate implementations and thus
 * implementations will need to follow the guidelines of the ServiceLoader
 * specifically a no-arg constructor is required.
 *
 * The return type of {@link #configure(LoggerContext)  configure} was changed from 'void' to
 * {@link ExecutionStatus) in logback version 1.3.0.
 */
public interface Configurator extends ContextAware {

    enum ExecutionStatus {
        NEUTRAL, // let the caller decide
        INVOKE_NEXT_IF_ANY, // invoke other
        DO_NOT_INVOKE_NEXT_IF_ANY
    }

    enum RankValue {
        FIRST,
        REGULAR,
        FALLBACK;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Rank {
        RankValue value() default RankValue.REGULAR;
    }

    /**
     * The context will also be set before this method is called via
     * {@link ContextAware#setContext(ch.qos.logback.core.Context)}.
     */
    ExecutionStatus configure(LoggerContext loggerContext);

}
