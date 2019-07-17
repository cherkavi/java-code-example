./gradlew shadowJar
java -jar build/libs/simscale-all.jar --input-file src/test/resources/small-log.txt --output-file out.txt --error-file error.txt