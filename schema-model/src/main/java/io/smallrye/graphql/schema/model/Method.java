package io.smallrye.graphql.schema.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a field on a type/input/interface
 * 
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
public final class Method extends Root {
    private Field returnField;
    private List<Field> parameters;

    public Field getReturn() {
        return returnField;
    }

    public void setReturn(Field returnField) {
        this.returnField = returnField;
    }

    public List<Field> getParameters() {
        return parameters;
    }

    public void setParameters(List<Field> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(Field parameter) {
        if (this.parameters == null) {
            this.parameters = new LinkedList<>();
        }
        this.parameters.add(parameter);
    }

    public boolean hasParameters() {
        return this.parameters != null && !this.parameters.isEmpty();
    }
}
