package com.schemaorg4j.codegen.factory;

import static com.schemaorg4j.codegen.constants.SchemaOrg4JConstants.DOMAIN_PACKAGE;

import com.schemaorg4j.codegen.domain.SchemaClass;
import com.schemaorg4j.codegen.jsonld.Util;

public class NextFieldContributor implements BlueprintContributor {

    @Override
    public void contribute(SchemaClass schemaClass, JavaPoetFileBlueprint blueprint) {
        blueprint.addField(Util.generateNextField(DOMAIN_PACKAGE, schemaClass.getLabel()));
    }
}
