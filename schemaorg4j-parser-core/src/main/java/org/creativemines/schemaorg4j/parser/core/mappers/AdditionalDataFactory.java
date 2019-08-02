package org.creativemines.schemaorg4j.parser.core.mappers;

import org.creativemines.schemaorg4j.domain.datatypes.SchemaOrg4JAdditionalData;
import org.creativemines.schemaorg4j.parser.core.domain.DataValue;
import org.creativemines.schemaorg4j.parser.core.domain.DataValueObject;
import org.creativemines.schemaorg4j.parser.core.domain.Field;

public class AdditionalDataFactory {

    private final DataValueObject additionalDataObject;

    public AdditionalDataFactory() {
        this.additionalDataObject = new DataValueObject("SchemaOrg4JAdditionalData");
    }

    public void track(Field f, DataValue dataValue) {
        additionalDataObject.putField(f.getName(), dataValue);
    }

    public SchemaOrg4JAdditionalData asJson() {
        SchemaOrg4JAdditionalData additionalData = new SchemaOrg4JAdditionalData();
        additionalData.setAdditionalData(additionalDataObject);
        return additionalData;
    }
}
