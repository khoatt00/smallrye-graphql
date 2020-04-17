package io.smallrye.graphql.client.impl.json;

import static io.smallrye.graphql.client.impl.json.GraphQlClientValueException.check;
import static javax.json.JsonValue.ValueType.FALSE;
import static javax.json.JsonValue.ValueType.TRUE;

import javax.json.JsonValue;

import io.smallrye.graphql.client.impl.reflection.TypeInfo;

class JsonBooleanReader extends Reader<JsonValue> {
    JsonBooleanReader(TypeInfo type, Location location, JsonValue value) {
        super(type, location, value);
    }

    @Override
    Object read() {
        assert value.getValueType() == TRUE || value.getValueType() == FALSE;
        check(location, value, boolean.class.equals(type.getRawType()) || Boolean.class.equals(type.getRawType()));
        return value.getValueType() == TRUE;
    }
}
