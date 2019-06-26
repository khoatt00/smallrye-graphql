package io.leangen.graphql.metadata.strategy.type;

import java.lang.reflect.AnnotatedType;

import io.leangen.graphql.metadata.exceptions.TypeMappingException;

@FunctionalInterface
public interface TypeTransformer {

    AnnotatedType transform(AnnotatedType annotatedType) throws TypeMappingException;
}
