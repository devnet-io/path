# path

Path is a simple java utility for providing strongly typed referces to nested methods and fields.

- [Getting Started](#getting-started)
- [Basic Usage](#basic-usage)
  - [With Methods](#using-methods)
  - [With Fields](#using-fields)
  - [Output](#output)
- [Use Cases](#use-cases)
  - [Database Queries](#strongly-typed-database-queries-using-criteria)
- [Implementation](#implementation)
- [Licensing](#licensing)

## Getting Started

Add the following dependency to your maven project. Gradle and others are also supported.

```xml
<dependency>
  <groupId>io.devnet.utils</groupId>
  <artifactId>utils-path</artifactId>
  <version>0.5.0</version>
</dependency>
```

## Basic Usage

#### Using Methods
Resolve the property path using getters

```java
String path = Path.for(Album.class)
  .$(Album::getArtist)
  .$(Artist::getGenre)
  .$(Genre::getName)
  .resolve();
  
System.out.println(path); // artist.genere.name
```

Shorthand syntax (max depth 5)

```java
String path = Path.for(Album::getArtist, Artist::getGenre, Genre::getName).resolve();
  
System.out.println(path); // artist.genere.name
```

#### Using Fields
Resolve property path using fields

```java
String path = Path.for(Album.class)
  .$(album -> album.artist)
  .$(artist -> artist.genre)
  .$(genre -> genre.name)
  .resolve();
  
System.out.println(path); // artist.genere.name
```

Shorthand syntax (max depth 5)

```java
String path = Path.for(a -> a.artist, a -> a.genre, g -> g.name).resolve();
  
System.out.println(path); // artist.genere.name
```

### Output 

Create the path chain

```java 
Path path = Path.for(Album.class).$(Album::getArtist).$(Artist::getGenere);
```
Return a string representation of the chain
```java 
String fieldPath = path.resolve(); // "artist.genere.name"

// include the parent in the path
String fullPath = path.resolve(Path.RESOLVE_PARENT); // "album.artist.genre.name"

```
Return a java.lang.reflect object for the last method in the chain. Throws MethodNotFound exception if the chain is built with fields.
```java 
Method method = path.lastMethod(); // getGenere()
```

Return the type of the last field in the chain
```java 
Class<?> field = path.lastField(); // String.class, return type of getGenere()
```

Retrieve the value for the last field in the chain by passing an instance of the parent class. The getters will be invoked if they are present in the chain. 
```java 
Album album = myService.getAlbum();

String value = path.evaluate(album); // "synthwave", return of the last method in the chain: getGenere()
```

## Use cases
### Strongly typed database queries using criteria

This syntax allows implementations to both enfoce the paths used in joins are correct and ensure that the input passed to the criteria is of the right type.

In this example Employee must have a method named getDepartment, department must have a method named getName, and
departmentName of the same type as Department$getName's return type.

```java
String departmentName = "accounting";

// shorthand

SearchResult<Employee> result = searchService.find(Employee.class)
  .eq(Employee::getDepartment, Department::getName, departmentName)
  .find();
 
// criteria approach
 
SearchCriteria<Employee> criteriaOne = SearchCriteria.for(Employee.class)
    .$(Employee::getDeplartment)
    .$(Department::getName)
    .eq(departmentName);

// or
SearchCriteria<Employee> criteriaTwo = SearchCriteria.for(Employee.class)
    .eq(Employee::getDeplartment, Department::getName, departmentName);
    
SearchResult<Employee> result = searchService.find(criteriaOne)
```

A classic implmentaiton may appear as follows. Here we do not know if the entity fields are and input types are valid until runtime. 

```java
String departmentName = "accounting";

// shorthand

SearchResult<Employee> result = searchService.find(Employee.class)
  .eq("department.name", departmentName) // method params String, Object
  .find();
 
// criteria approach

SearchCriteria<Employee> criteriaTwo = SearchCriteria.for(Employee.class)
    .eq("department.name", departmentName); // method params: String, Object
    
SearchResult<Employee> result = searchService.find(criteriaOne);
```

## Implementation

Path provides a convient syntax on top of [Jodd's MethodRef](https://jodd.org/ref/methref.html) for chaining multiple references together. The method are never invoked.
