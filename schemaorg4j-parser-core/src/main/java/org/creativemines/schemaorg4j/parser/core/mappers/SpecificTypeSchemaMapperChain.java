package org.creativemines.schemaorg4j.parser.core.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.creativemines.schemaorg4j.parser.core.SchemaMapper;

public class SpecificTypeSchemaMapperChain {

    private final List<SpecificTypeSchemaMapper> chain;

    public SpecificTypeSchemaMapperChain(SchemaMapper mapper) {
        this.chain = new ArrayList<SpecificTypeSchemaMapper>() {{
            add(new StringMapper());
            add(new BooleanMapper());
            add(new IntegerMapper());
            add(new FloatMapper());
            add(new TimeMapper());
            add(new DateTimeMapper());
            add(new DateMapper());
            add(new EnumMapper());
            add(new SimpleValueMapper());
            add(new SimpleObjectMapper(mapper));
            add(new ParameterizedOrTypeMapper(mapper));
            add(new ComplexOrTypeMapper(mapper));
            add(new ListValueMapper(mapper));
            add(new NullMapper());
        }};
    }

    public Optional<Object> handle(MappingArguments arguments) {
        return chain.stream()
            .filter(
                mapper -> mapper.canHandle(arguments.getParameterClass(), arguments.getArgument()))
            .findFirst()
            .map(mapper -> mapper.handle(arguments));
    }
}
