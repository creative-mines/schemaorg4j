# SchemaOrg4J

SchemaOrg4J is a library for Java which provides a properly typed translation of [schema.org](http://schema.org) structured data to Java beans.  Take for example a Book defined in Schema.org style JSON-LD:

```json

{
   "@type":"Book",
   "author":"/author/jd_salinger.html",
   "bookFormat":"http://schema.org/Paperback",
   "datePublished":"1991-05-01",
   "image":"catcher-in-the-rye-book-cover.jpg",
   "inLanguage":"English",
   "isbn":"0316769487",
   "name":"The Catcher in the Rye",
   "numberOfPages":"224",
   "offers":{
      "@type":"Offer",
      "availability":"http://schema.org/InStock",
      "price":"6.99",
      "priceCurrency":"USD"
   },
   "publisher":"Little, Brown, and Company",
   "aggregateRating":{
      "@type":"AggregateRating",
      "ratingValue":"4",
      "reviewCount":"3077"
   },
   "review":[
      {
         "@type":"Review",
         "author":"John Doe",
         "datePublished":"2006-05-04",
         "name":"A masterpiece of literature",
         "reviewBody":"I really enjoyed this book. It captures the essential challenge people face as they try make sense of their lives and grow to adulthood.",
         "reviewRating":{
            "@type":"Rating",
            "ratingValue":"5"
         }
      }
   ]
}
```

Supposing we have parsed this entry (using `schemaorg4j-parser-json`), we'll be able to do the following:

```java
Book book = ... // Parse the json structure into a Book
book.getPublisher().getSimpleValue(); // "Little, Brown, and Company"

Book.AggregateRating
            .oAndThen(as(Rating.class))
            .oAndThen(AggregateRating.RatingValue)
            .oAndThen(OrText.Value())
            .get(book)
            .getValue(); // 4

book.getReview().asReviewList().get(0).getReviewBody() // "I really enjoyed ..."
```

## Structure of Schema.org data

On the surface, Schema.org data is fairly rigid.  Each field has a type specified by the schema and all fields that are not primitive must have a type.  In actuality, Schema.org is fairly flexible which makes consuming it a bit difficult.  On ingestion, all Schema.org values can be of a few types:

* A primitive type like Integer, String, Boolean, [etc](https://schema.org/DataType)
* An object like Book or Event (enumeration values are also objects)
* Specifically a [Text](https://schema.org/Text) value or another type (like Integer or Book)
* One of multiple types (Person or Organization)

Additionally, the following rules apply:

* Any field which can be an object type (like `author` which is either an `Organization` or `Person`) can also be a simple string (as in the example above)
* Any field may also contain a list instead of an object or primitive type
* Some fields such as `review` and `reviews` actually point to the same data

Because there are so many cases, handling raw JSON in Schema.org format can be quite difficult.  SchemaOrg4J generates classes to support all possible values of valid Schema.org structured data while also supporting error handling and extension fields.

## Primitive types

SchemaOrg4J provides primitive objects for all classes which inherit from `DataType` in Schema.org.  These objects are then mapped internally to Java types as follows:

| Schema.org Type | Java Data Type |
| --------------- | -------------- |
| Text            | String         |
| Integer         | Integer        |
| Number          | Float          |
| Boolean         | Boolean        |
| Date            | LocalDate      |
| Time            | LocalTime      |
| DateTime        | ZonedDateTime  |
| Float           | Float          |
| URL             | String         |

Each data type object provides `equals`, `hashCode`, a Lens (see lensing below) to access the embedded data and a `getValue` method which gets the underlying Java type.

## Object types

Each object type (such as Event, Book, etc) has two different Java types generated for it

* An interface bearing the name of the type (such as `Book`)
* A concrete class implementing this interface (such as `BookImpl`)

The reason classes are generated this way is because Schema.org supports multiple inheritance for its types.  `LocalBusiness` is a good example.  In SchemaOrg4J, `LocalBusiness` will have an interface generated:

```java
public interface LocalBusiness extends Organization, Place {
    ... // Getters and setters, etc
}
```

and a concrete implementation

```java
public class LocalBusinessImpl implements LocalBusiness {
    ... // Fields
}
```

Fields, getters, and setters are generated from the union of the fields of all super-types to support the interface for an object type.

Object types also implement a method `asXList` where X is the name of the class (`asBookList`, `asCreativeWorkList`, etc).  If the value of a field of the object's type actually stored a list instead of a single value, the first value will be the object.  All subsequent values can either be accessed by traversing `getNextX` (`getNextBook`, `getNextCreativeWork`, etc) or by calling `asXList` which will generate a random access list and cache the result.

```json
{
  "@type": "Book",
  "review":[
      {
         "@type":"Review",
         "author":"John Doe",
         "datePublished":"2006-05-04",
         "name":"A masterpiece of literature",
         "reviewBody":"I really enjoyed this book. It captures the essential challenge people face as they try make sense of their lives and grow to adulthood.",
         "reviewRating":{
            "@type":"Rating",
            "ratingValue":"5"
         }
      }
   ]
}
```

`review` is a list instead of a `Review` type object, therefore parsing this we could obtain

```
Book book = ...
Review review = book.getReview() // Returns the first review object in the list
review.asReviewList().size // 1
review.getNextReview() // null (there is no next review)
review.getReviewList() // Returns the random access list.  Note that the list will not be 						 // regenerated, it is cached in the review object
```

Object types also contain lenses for each public field (see lensing below).

Finally, if the value was a simple value (as is the case for author in the above example), you may call `getSimpleValue` which always returns a string (in this case `John Doe`)

```java
book.getSimpleValue() // John Doe
```

## Or Types

In many cases, the type of a field can be one of a set of types.  Java does not natively support `Either` types, so SchemaOrg4J provides two mechanisms to support these instances.

### Complex Or Types

If a field may be one of two or more types (Such as `Organization` or `Person`) a type will be generated that is the union of both types.  For each type, a getter and a setter of the appropriate type will be generated on the object, as well as a lens.  In our example, `OrganizationOrPerson` would be generated as follows:

```
public class OrganizationOrPerson {
  public static final Lens<OrganizationOrPerson, Organization> Organization;
  public static final Lens<OrganizationOrPerson, Person> Person;

  private Organization organization;
  private Person person;
  private String simpleValue;

  private OrganizationOrPerson nextOrganizationOrPerson;

  public void setOrganization(Organization organization) { ... }
  public Organization getOrganization(){ ... }

  public void setPerson(Person person) { ... }
  public Person getPerson() { ... }

  public OrganizationOrPerson getNextOrganizationOrPerson() { ... }
  public List<OrganizationOrPerson> asOrganizationOrPersonList() { ... }
  
  public String getSimpleValue() {}

```

This code is illustrative but shows most of the relevant methods.  One and only one of either `Person`, `Organization`, or `simpleValue` **should** be non-null, but this is not guaranteed.

### `OrText<T>`

Many values have a type of `XorText` where `X` is some other object type or primitive type in Schema.org.  In order to avoid generating more classes than necessary, SchemaOrg4J provides a generic `OrText` class which allows getting the value as either text or the object type.  The same general interface is served by `OrText` as complex or type classes and the same rule regarding only one of the fields being set to a non-null value applies.  If the field was a simple string value, it will be in the `text` field.

## Errors

If the Schema.org metadata was incorrectly generated, some fields may be of the wrong type.  All SchemaOrg4J classes support a `getSchemaOrg4JErrors` method which stores a list of `SchemaOrg4JError` objects.  These objects contain a `String` error message and an `Object` contents.  The contents are the data that could not be parsed into regular SchemaOrg4J classes.

Take the following example

```json
{
	"@context": "http://schema.org",
	"@type": "MusicEvent",
	"actor": {
		"@type": "Book"
	}
}
```

The `actor` field cannot be an object of type `Book`, therefore a `MusicEvent` object will be generated and the `actor` field will contain a `Person` object.  This `Person` object will have all fields set to `null` but have one entry in the list of `SchemaOrg4JErrors`

```java
MusicEvent musicEvent = ...
musicEvent.getActor().getSchemaOrg4JErrors().size() // 1
```

The exact contents of `SchemaOrg4JError#contents` depend on the parser used to generate it.  If you are using `schemaorg4j-parser-core` or a library which relies on it, this field will be populated with either a `DataValueString`, `DataValueObject` or `DataValueList` (see that project for documentation on these objects).

## Additional Data

In some cases, additional data may be embedded in an object which are not specified for the type of object. 

```
{
    "@type": "Book",
    "x-google-store-offers": {
    	"@type": "Offer",
    	"availability": "http://schema.org/InStock",
    	"price": "6.99",
    	"priceCurrency": "USD"
    }
}
```

In this case, `x-google-store-offers` is listing an `Offer` object for the `Book`.  `Book` does not support an `x-google-store-offers` field, therefore this extra data is placed in an instance of `SchemaOrg4JAdditionalData`.  The exact `additionalData` value will vary based on which parser generated the SchemaOrg4J class, but if you are using `schemaorg4j-parser-core`, it will contain an instance of `DataValueObject` which has all of the fields and their values (see the `schemaorg4j-parser-core` project for documentation on this object).

## Lensing

A lens is a functional programming concept that allows reading and writing immutable data structures nested at great depth.  While the lenses used in SchemaOrg4J do not perform immutable updates (they mutate the underlying objects), they operate in a similar way to Lenses in libraries for functional languages.

```java
Book.AggregateRating
            .oAndThen(as(Rating.class))
            .oAndThen(AggregateRating.RatingValue)
            .oAndThen(OrText.Value())
            .get(book)
            .getValue(); // 4
```

This example (taken from the top of this README) illustrates the use of a Lens to extract the rating value as a number which is nested somewhat deeply within a Book.  It replaces the following code

```java
try {
	book.getAggregateRating().getRatingValue().getValue().getValue();
} catch (NullPointerException e) {
    // Handle the null pointer
}
```

The `NullPointerException` that would be thrown if anything along the getter chain was null is ignored when using Lensing with `o` methods.  Lensing is best used when you are dealing with a structure that you expect to be in a certain format and you don't care about structures not in that format.

### `o` Methods in Lensing

Lenses have two types of methods, those prefixed with an `o` and those not.  The `o` stands for optional.  Any method prefixed with `o` will not throw a `NullPointerException` if any object along the chain is `null` or is not of the appropriate type.  It is recommended that if you are experimenting with a Lens, you first build it without `o` methods.  Then in production if you are sure you will not need to know about exceptions in your Lenses because of badly structured data, use the `o` methods to avoid throwing errors.

Methods that are not prefixed with `o` will throw a `LensException` when data is not in the expected format.

## Javadocs

Javadocs for SchemaOrg4J are hosted [here]().

## Related projects

`schemaorg4j-parser-core` - Parser library which serves as the basis for all parsers (json, html, etc).

`schemaorg4j-parser-json` - Parses json into SchemaOrg4J structures.

