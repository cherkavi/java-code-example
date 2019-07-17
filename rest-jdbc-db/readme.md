spring boot application with collaboration with DB via REST

### create status
```
curl -i -X POST -H "Content-Type:application/json" -d "{  \"status\" : \"NEW\",  \"key\" : \"test_01\" }" http://localhost:8080/status
```


### update status
```
curl -i -X PUT -H "Content-Type:application/json" -d "{  \"status\" : \"DEPLOYING\",  \"key\" : \"test_01\" }" http://localhost:8080/status/1
```


### find all 
```
http://localhost:8080/status/search/findBy
```

### read data by ID
http://localhost:8080/status/1


### find by key
http://localhost:8080/status/search/findByKey?key=test_01