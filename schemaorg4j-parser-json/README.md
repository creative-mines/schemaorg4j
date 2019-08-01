# schemaorg4j-parser-json

`schemaorg4j-parser-json` converts Schema.org structured JSON into the intermediate format exposed by `schemaorg4j-parser-core` and relies on that project to generate objects from the `schemaorg4j` project.

`schemaorg4j-parser-json` uses Jackson internally to parse JSON into objects of the appropriate types.

## Example use

```
DataValueObject dvo = map({
    "@type": "Book",
    "name": "Moby Dick",
    ...
});

DataValueList dvl = mapList([{
   "@type": "Book",
   "name": "Moby Dick",
   ...
}, ...])
```

Objects of type `DataValueList` and `DataValueObject` may then be fed as input into relevant interfaces from `schemaorg4j-parser-core` to extract the `schemaorg4j` objects.