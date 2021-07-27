package io.smallrye.graphql.client.dynamic.core;

import io.smallrye.graphql.client.core.FieldOrFragment;
import io.smallrye.graphql.client.core.exceptions.BuildException;

public class OperationImpl extends AbstractOperation {
    // TODO: Use simple StringJoiner
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        switch (this.getType()) {
            case QUERY:
                builder.append("query");
                break;
            case MUTATION:
                builder.append("mutation");
                break;
            case SUBSCRIPTION:
                builder.append("subscription");
                break;
            default:
                throw new BuildException("Operation type must be one of QUERY, MUTATION or SUBSCRIPTION");
        }

        builder.append(" ");

        builder.append(this.getName());

        if (!this.getVariables().isEmpty()) {
            _buildVariables(builder);
        }

        if (!this.getFields().isEmpty()) {
            _buildFields(builder);
        } else {
            throw new BuildException("An operation must have at least one root field.");
        }

        return builder.toString();
    }

    // TODO: Use StringJoiner  or Stream + Collectors.joining (https://www.baeldung.com/java-strings-concatenation)
    private void _buildVariables(StringBuilder builder) {
        builder.append("(");

        VariableImpl[] vars = this.getVariables().toArray(new VariableImpl[0]);
        for (int i = 0; i < vars.length; i++) {
            VariableImpl variable = vars[i];
            builder.append(variable.build());
            if (i < vars.length - 1) {
                builder.append(", ");
            }
        }

        builder.append(")");
    }

    // TODO: Use StringJoiner  or Stream + Collectors.joining (https://www.baeldung.com/java-strings-concatenation)
    private void _buildFields(StringBuilder builder) {
        builder.append("{");

        FieldOrFragment[] rootFields = this.getFields().toArray(new FieldOrFragment[0]);
        for (int i = 0; i < rootFields.length; i++) {
            FieldOrFragment rootField = rootFields[i];
            builder.append(rootField.build());
            if (i < rootFields.length - 1) {
                builder.append(" ");
            }
        }

        builder.append("}");
    }
}
