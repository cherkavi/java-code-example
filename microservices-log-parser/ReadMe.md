### scripts for fast check
build-and-execute.sh
compare-results.sh


### How to build and execute 'manually':
- ./gradlew shadowJar
- java -jar build/libs/simscale-all.jar --input-file src/test/resources/small-log.txt
