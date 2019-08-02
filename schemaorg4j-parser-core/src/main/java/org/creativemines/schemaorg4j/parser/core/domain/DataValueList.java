package org.creativemines.schemaorg4j.parser.core.domain;

import java.util.ArrayList;
import java.util.List;

public class DataValueList implements DataValue {

    private List<DataValue> list;

    public DataValueList() {
        this.list = new ArrayList<>();
    }

    public List<DataValue> getList() {
        return list;
    }

    public void addValue(DataValue dataValue) {
        this.list.add(dataValue);
    }
}
