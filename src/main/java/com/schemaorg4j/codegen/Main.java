package com.schemaorg4j.codegen;

import static com.schemaorg4j.codegen.constants.Schema4JConstants.DOMAIN_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaGraph;
import com.schemaorg4j.codegen.factory.JavaFileFactory;
import com.schemaorg4j.codegen.jsonld.Util;
import com.squareup.javapoet.JavaFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] arguments) throws IOException {

        Main main = new Main();
        URL schemaDefinitionResource = main.getClass().getClassLoader().getResource("all-layers.jsonld");

        if (schemaDefinitionResource == null) {
            LOGGER.error("schema file was not found, exiting");
            return;
        }

        SchemaGraph graph = Util.getObjectMapperInstnace()
            .readValue(schemaDefinitionResource, SchemaGraph.class);

        LOGGER.info("Generating {} classes", graph.getClasses().size());
        JavaFileFactory factory = new JavaFileFactory(DOMAIN_PACKAGE);
        Collection<JavaFile> javaFiles = factory
            .buildJavaFiles(graph);

        System.out.println(System.getProperty("user.dir"));
        javaFiles.forEach(file -> {
            try {
                file.writeTo(new File("/home/adam/code/schemaorg4j/temp"));
            } catch (IOException e) {
                LOGGER.error("Error while writing files");
            }
        });
    }
}
