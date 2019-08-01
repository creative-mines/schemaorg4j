# schemaorg4j-parser-core

`schemaorg4j-parser-core` exposes an intermediate format which other parsers can target as a translation from their data formats (json, html, etc) to translate data into the `schemaorg4j` beans.  Put another way, `schemaorg4j-parser-core` accepts a specific intermediate input format and outputs `schemaorg4j` classes.

## Example use

Mapping a simple object (which has the same interface for mapping lists of objects)

```java
DataValueObject dataValueObject = fromJson("{\n"
            + "        \"@context\": \"http://schema.org\",\n"
            + "        \"@type\": \"TouristAttraction\",\n"
            + "        \"name\": \"Musée Marmottan Monet\",\n"
            + "        \"description\": \"It's a museum of Impressionism and french ninenteeth art.\",\n"
            + "        \"event\": {\n"
            + "                \"@type\": \"Event\",\n"
            + "                \"about\": [\"Hodler\",\"Monet\",\"Munch\"],\n"
            + "                \"name\": \"Peindre l'impossible\",\n"
            + "                \"startDate\": \"2016-09-15\",\n"
            + "                \"endDate\": \"2017-01-22\"\n"
            + "        }\n"
            + "}");

SchemaMapper mapper = new SchemaMapper();
TouristAttraction attraction = (TouristAttraction) mapper.map(dataValueObject);
```



Handling objects of a specific type with `SchemaHandler`.  This is convenient when you only care about extracting data of specific types and you don't care about the rest of the schema.

```java
DataValueList dataValueList = fromJson("[{\n"
            + "  \"@context\": \"http://schema.org\",\n"
            + "  \"@type\": \"MusicEvent\",\n"
            + "  \"name\": \"Shostakovich Leningrad\",\n"
            + "  \"performer\": [\n"
            + "    {\n"
            + "      \"@type\": \"MusicGroup\",\n"
            + "      \"name\": \"Chicago Symphony Orchestra\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Jaap van Zweden\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Bach\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Godel\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"@type\": \"Person\",\n"
            + "      \"name\": \"Escher\"\n"
            + "    }\n"
            + "  ]\n"
            + "}]", DataValueList.class);

List<Person> people = new ArrayList<>();

SchemaHandler handler = new SchemaHandler(new SchemaMapper());
handler.addHandler(Person.class, people::add).consume(dataValueList);

Set<String> nameSet = people
    .stream()
    .map(Person::getName)
    .collect(Collectors.toSet());
        	
assertTrue(nameSet.contains("Jaap van Zweden"));
assertTrue(nameSet.contains("Bach"));
assertTrue(nameSet.contains("Godel"));
assertTrue(nameSet.contains("Escher"));
```



## Input Format

`schemaorg4j-parser-core` exposes a `DataValue` interface with three implementations:

* `DataValueList` which is a list of `DataValue` objects
* `DataValueObject` which contains a mapping of `Field` objects to `DataValue`s
* `DataValueString` which contains strings and ways to parse them into Schema.org `DataType` objects

All primitive types are meant to be handled with instances `DataValueString` class.  All object types should be handled by `DataValueObject` and lists of either type (possibly heterogeneous lists) are handled by `DataValueList`

### Sample translation

Given the following JSON in the structure of Schema.org

```json
{
    "@context": "http://schema.org",
    "@type": "TouristAttraction",
    "name": "Musee Marmottan Monet",
    "description": "It's a museum of impressionism and french ninenteeth art.",
    "event": {
        "@type": "Event",
        "about": ["Holder", "Monet", "Munch"],
        "name": "Peindre L'impossible",
        "startDate": "2016-09-15",
        "endDate": "2017-01-22"
    }
}
```

`schemaorg4j-parser-core` will expect the following input for mapping to `schemaorg4j` classes:

```
DataValueObject(
  "type": "TouristAttraction",
  Field("name"): DataValueString("Musee Marmottan Monet"),
  Field("description"): DataValueString("It's a museum of impressionism and french ninenteeth art."),
  Field("event"): DataValueObject(
    "type": "Event",
    Field("about"): DataValueList(
      DataValueString("Holder"),
      DataValueString("Monet"),
      DataValueString("Munch")
    ),
    Field("name"): DataValueString("Peindre L'impossible"),
    Field("startDate"): DataValueString("2016-09-15"),
    Field("endDate"): DataValueString("2017-01-22")
  )
)
```

### `DataValueList`

`DataValueList` instances may be composed of any combination of `DataValueObject`, `DataValueString` or `DataValueList`.  No guarantee is made that the combinations will actually parse correctly, that depends entirely on the actual schema of the object being parsed.

### `DataValueObject`

The `type` field is required for any instance of `DataValueObject`.  Either short or long Schema.org types are accepted so:

* `Book`
* `http://schema.org/Book`
* `https://schema.org/Book`

Will all work correctly.

### DataValueString

`DataValueString` objects accept a `String` value and provide methods to parse the value as any of the `DataType`s exposed by Schema.org.  Parser errors must be handled at the call site.  Time parsing methods accept custom parsers:

* `asDate`

* `asDateTime`
* `asTime`

The interfaces of the custom parsers are defined in `DateParser`, `DateTimeParser`, and `TimeParser` respectively.

### Field

Fields at this time are simply wrapped strings.  They may be expanded at a later time to be more than simple wrappers around a `String`.

## Parsing

There are essentially 6 "layers" of parsing the data into `schemaorg4j` objects

### Simple `DataType` primitives

The parser determines the correct type of the primitive target type based on the data type of the field the data will be stored in on a target object (using reflection).  After determining which data type to target, the appropriate `as` method on `DataValueString` is called (`asDateTime` for example).

### Object types

Simple object types which are not composed of multiple possible types are mapped recursively.  See `SimpleObjectMapper` for details.

### Parameterized Or types

Parameterized or types are somewhat tricky.  There are two possibilities in a parameterized or type:

1. The other type (non text type) is a simple object type in which case we recurse into parsing a simple object (see above)
2. The other type is a primitive

Setters on `schemaorg4j` objects which accept parameterized or types as an argument annotate their parameter with `@SchemaOrg4JOrType` which contains a reference to the 'other' class.  Reflection is used to attempt to map the string value to a primitive of the type specified by the `@SchemaOrg4JOrType` annotation.  If this mapping fails, then the actual value is a `Text` type.

### Complex Or types

The algorithm to map a complex or type is somewhat intricate but boils down to this psuedocode:

```
If the value being mapped is an instance of `DataValueObject`:
	Attempt to see which field involved in the complex type can support the value
	If one can, set that field
	If none can, error
	
Otherwise, iterate through each primitive type that the Or type could be composed of
	If the value can be parsed to the data type
		set the field and exit
		
No field was found that could support this value, error
		
```

It's important to note that "supporting  the value" means considering the inheritance hierarchy as well.  For example if an object accepts types of `Event` and the value is a `MusicEvent`, that field should be set.

**NOTE**: the parser is not necessarily smart enough to choose the *best* match.  Thus if a complex or type supports objects of both `Thing` and `Event`, the actual object may be set to `Thing` even though `Event` is a better match.  This is a target for future enhancement.

### Additional fields

Sometimes a setter on a `schemaorg4j` object cannot be found for a field of a certain type (extensions to the schema, deprecated fields not captured in the schema's current revision, etc).  In this case, the `schemaOrg4JAdditionalData` field on the parsed object will contain a `DataValueObject` which has all fields and their values:

```
DataValueObject(
  "type": "SchemaOrg4JAdditionalData",
  Field("x-extension-field"): DataValueList(...),
  Field("x-deprecated-field"): DataValueObject(...)
)
```

This data may even be parsed if it is in an expected format by feeding it back into the parser as is

```
Book book = WebPage.MainEntity.andThen(as(Book.class)).get(webPage);

DataValueObject additionalData = (DataValueObject) book
	.getSchemaOrg4JAdditionalData()
	.getAdditionalData();

Offer offer = (Offer) new SchemaMapper().map(
	(DataValueObject) additionalData.getValueFor("sellingOffers"));
assertEquals(offer.getPrice().getValue().getValue(), 6.99, 0.1);
```

### Errors

Any fields which:

* Are of an unknown `@type`
* Are of the wrong data type
* Are in the wrong format (object expected in place of a primitive, primitive expected and list received, etc)
* Cannot be parsed

Or which otherwise generate an error for some reason will end up in `schemaOrg4JErrors` in whatever their original format was.  If a list is given in place of a primitive for example, the `schemaOrg4JError` object will contain that list in full.

## Output Format

`SchemaMapper` outputs either a `Thing` or `List<Thing>` depending on which argument is provided (`DataValueObject` or `DataValueList`).  It is possible to request output of a specific class, but this is done by simple casting.  There are no guarantees made that a `ClassCastException` will not occur if the result class is of an unexpected type.

See the `schemaorg4j` project for more information on the `schemaorg4j` classes that this project is capable of outputting.