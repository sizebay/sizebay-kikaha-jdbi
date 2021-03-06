# sizebay-kikaha-jdbi
Provide tight integration of Jdbi3 for the just released Kikaha 2.0.x version.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.sizebay.kikaha/sizebay-kikaha-jdbi/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.sizebay.kikaha/sizebay-kikaha-jdbi)

## Dependencies
If you use Maven:
```xml
<dependency>
    <groupId>com.sizebay.kikaha</groupId>
    <artifactId>sizebay-kikaha-jdbi</artifactId>
    <version>0.1.0</version>
</dependency>
```
If you use Gradle:
```gradle
dependencies {
    compile group: 'com.sizebay.kikaha', name: 'sizebay-kikaha-jdbi', version: '0.1.0'
}
```
If you are using the Kikaha's command line tool:
```bash
kikaha project add_dep 'com.sizebay.kikaha:sizebay-kikaha-jdbi:0.1.0'
```

## Getting Started
This plugin was made with _simplicity_ in mind, so it forces developers to use the Jdbi3's
[SQL Object API](http://jdbi.org/sql_object_overview/), once it is stable and easier to use.
It requires Java 8 and does not support Jdbi's DAO made with Abstract Classes, despite the fact
it has full support of DAOs made with Interface and its default methods.

### Injecting your DAOs
Basically, once you have setup 'sizebay-kikaha-jdbi' as dependency, you can inject any Jdbi DAO
on your Kikaha's managed services, all you need is annotate your DAO _interface_ with the
```sizebay.kikaha.jdbi.JDBI``` annotation.

```java
import org.jdbi.v3.sqlobject.*;
import sizebay.kikaha.jdbi.JDBI;

@JDBI
public interface StatisticsDAO {

  @SqlQuery( "SELECT COUNT(*) FROM users" )
  long countHowManyUsersIsSavedOnMyProduct();
}

import javax.inject.*;
import kikaha.urouting.api.*;

@Path( "stats" )
@Singleton
public class StatisticsResource {

  @Inject StatisticsDAO dao;

  @GET
  public long countHowManyUsersIsSavedOnMyProduct() {
    return dao.countHowManyUsersIsSavedOnMyProduct();
  }
}
```

### Mapping Entities
Out-of-box 'sizebay-kikaha-jdbi' has a very simple mapping system configured. Despite the fact it does not handle
One To Many/Many To Many/Many To One aggregations, it is very simple and easy to use. Basically, you have three annotations:
- ```sizebay.kikaha.jdbi.serializers.Entity```: (mandatory) let Jdbi know that 'sizebay-kikaha-jdbi' will be the Column Mapper of this class.
- ```sizebay.kikaha.jdbi.serializers.Column```: identify an field that it should be mapped.
- ```sizebay.kikaha.jdbi.serializers.Optional```: make a column optional. Columns not marked as optional will raise exception
during the mapping process if no column with its name was found at the query.

By default, it will bind any field mapped with the ```@Column``` annotation. It will use the field's name as column
identification. If want to use annotation name as identifier, you can defined a name through the ```Column.value```.

[Here](https://github.com/Skullabs/kikaha-jdbi-sample/blob/master/source/test/User.java) you can see a simple example application
using the custom mapper.

## Contributing
This software is released as Open Source to help people to take advantage of Jdbi simplicity on its Kikaha development
enviroment. We will be glad with anyone that use, improve and suggest enhancements on this API.

## License
Apache License 2

