package org.creativemines.schemaorg4j.codegen.factory;

import static org.creativemines.schemaorg4j.SchemaOrg4JConstants.DOMAIN_PACKAGE;

import org.creativemines.schemaorg4j.codegen.domain.SchemaClass;
import org.creativemines.schemaorg4j.codegen.feature.NextFieldFeature;

public class NextFieldContributor implements BlueprintContributor {

    private final NextFieldFeature nextFieldFeature;

    public NextFieldContributor() {
        this.nextFieldFeature = new NextFieldFeature(DOMAIN_PACKAGE);
    }


    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        nextFieldFeature.build(schemaClass.getLabel())
            .handle(blueprint::addField, blueprint::addMethod);
    }
}
