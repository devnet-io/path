# path

Path is a simple java utility for providing strongly typed referces to nested methods and fields.

- [Getting Started](#getting-started)
- [Basic Usage](#basic-usage)
  - [With Methods](#using-methods)
  - [With Fields](#using-fields)
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
String path = Path.for(a -> a.artist, a -> a.genre, g -> genre.name).resolve();
  
System.out.println(path); // artist.genere.name
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
