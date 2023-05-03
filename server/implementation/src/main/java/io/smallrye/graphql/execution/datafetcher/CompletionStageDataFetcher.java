package io.smallrye.graphql.execution.datafetcher;

import java.util.List;
import java.util.concurrent.CompletionStage;

import graphql.schema.DataFetchingEnvironment;
import io.smallrye.graphql.schema.model.Operation;
import io.smallrye.graphql.schema.model.Type;
import io.smallrye.mutiny.Uni;

/**
 * Handle Async calls with CompletableFuture
 *
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 * @param <K>
 * @param <T>
 */
public class CompletionStageDataFetcher<K, T> extends AbstractAsyncDataFetcher<K, T> {

    public CompletionStageDataFetcher(Operation operation, Type type) {
        super(operation, type);
    }

    @Override
    protected Uni<?> handleUserMethodCall(DataFetchingEnvironment dfe, final Object[] transformedArguments)
            throws Exception {
        return Uni.createFrom()
                .completionStage((CompletionStage<?>) operationInvoker.invoke(transformedArguments));
    }

    @Override
    protected Uni<List<T>> handleUserBatchLoad(DataFetchingEnvironment dfe, final Object[] arguments) throws Exception {
        return Uni.createFrom().completionStage((CompletionStage<List<T>>) operationInvoker.invoke(arguments));
    }
}
