package org.icervantes.uber.email.inject.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * Annotation for SendGridLabel.
 * 
 * @author Ivan Cervantes (lord.icervantes@gmail.com)
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface SendGridLabel {
}