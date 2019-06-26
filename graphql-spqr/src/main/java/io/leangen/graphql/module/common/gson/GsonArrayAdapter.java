package io.leangen.graphql.module.common.gson;

import java.lang.reflect.AnnotatedType;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.generator.mapping.OutputConverter;
import io.leangen.graphql.generator.mapping.common.AbstractTypeSubstitutingMapper;

public class GsonArrayAdapter extends AbstractTypeSubstitutingMapper<List<JsonElement>>
        implements OutputConverter<JsonArray, List> {

    private static final AnnotatedType JSON = GenericTypeReflector.annotate(JsonElement.class);

    @Override
    public List convertOutput(JsonArray original, AnnotatedType type, ResolutionEnvironment resolutionEnvironment) {
        List<Object> elements = new ArrayList<>(original.size());
        original.forEach(element -> elements.add(resolutionEnvironment.convertOutput(element, JSON)));
        return elements;
    }

    @Override
    public boolean supports(AnnotatedType type) {
        return GenericTypeReflector.isSuperType(JsonArray.class, type.getType());
    }
}
