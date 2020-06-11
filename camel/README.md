https://camel.apache.org/manual/latest/camel-maven-archetypes.html

```sh
# https://repository.apache.org/content/groups/snapshots-group/org/apache/camel/archetypes/3.4.0-SNAPSHOT/maven-metadata.xml
mvn archetype:generate \
  -DarchetypeGroupId=org.apache.camel.archetypes \
  -DarchetypeArtifactId=camel-archetype-java \
  -DarchetypeVersion=3.4.0-SNAPSHOT \
  -DarchetypeRepository=https://repository.apache.org/content/groups/snapshots-group
```


```sh
mvn camel:run
```


