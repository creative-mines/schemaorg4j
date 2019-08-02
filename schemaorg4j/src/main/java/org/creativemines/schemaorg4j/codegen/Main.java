package org.creativemines.schemaorg4j.codegen;

import com.squareup.javapoet.JavaFile;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.creativemines.schemaorg4j.codegen.domain.SchemaGraph;
import org.creativemines.schemaorg4j.codegen.factory.JavaFileFactory;
import org.creativemines.schemaorg4j.codegen.jsonld.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String SOURCE_LOCATION = "build/generated-src/main/java";

    public static void main(String[] arguments) throws IOException {

        Main main = new Main();
        URL schemaDefinitionResource = main.getClass().getClassLoader()
            .getResource("all-layers.jsonld");

        if (schemaDefinitionResource == null) {
            LOGGER.error("schema file was not found, exiting");
            return;
        }

        SchemaGraph graph = Util.getObjectMapperInstnace()
            .readValue(schemaDefinitionResource, SchemaGraph.class);

        LOGGER.info("Generating {} classes", graph.getClasses().size());
        JavaFileFactory factory = new JavaFileFactory();
        Collection<JavaFile> javaFiles = factory
            .buildJavaFiles(graph);

        javaFiles.forEach(file -> {
            Path path = Paths.get(System.getProperty("user.dir"), SOURCE_LOCATION);
            try {
                file.writeTo(path);
            } catch (IOException e) {
                LOGGER.error("Error writing file", e);
            }
        });
    }
}
