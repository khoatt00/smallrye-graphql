/*
 * Copyright 2020 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.smallrye.graphql;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.jandex.Index;
import org.jboss.jandex.IndexReader;
import org.jboss.logging.Logger;

import graphql.schema.GraphQLSchema;
import io.smallrye.graphql.index.IndexInitializer;
import io.smallrye.graphql.schema.GraphQLSchemaInitializer;

/**
 * Bootstrap MicroProfile GraphQL
 * 
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class Bootstrap {
    private static final Logger LOG = Logger.getLogger(Bootstrap.class.getName());

    @Inject
    private IndexInitializer indexInitializer;

    @Inject
    private GraphQLSchemaInitializer graphQLSchemaInitializer;

    @Produces
    private Index index;

    @Produces
    private GraphQLSchema graphQLSchema;

    public GraphQLSchema generateSchema() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream("META-INF/jandex.idx")) {
            IndexReader reader = new IndexReader(stream);
            Index i = reader.read();
            LOG.info("Loaded index from [META-INF/jandex.idx]");
            return indexToGraphQLSchema(i);
        } catch (IOException ex) {
            Index i = indexInitializer.createIndex();
            LOG.info("Loaded index from generation via classpath");
            return indexToGraphQLSchema(i);
        }
    }

    public GraphQLSchema generateSchema(Index i) {
        LOG.info("Loaded index from provided index");
        return indexToGraphQLSchema(i);
    }

    private GraphQLSchema indexToGraphQLSchema(Index index) {
        this.index = index;
        this.graphQLSchema = graphQLSchemaInitializer.getGraphQLSchema();
        return this.graphQLSchema;
    }

}
