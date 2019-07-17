Application to upload and read files via HTTP connection.

upload example
```
curl -X PUT -H "Content-Type: multipart/form-data" -F "file=@FileUploadController.java;filename=1.java" http://127.0.0.1:8090
```


download example
```
curl -X GET  http://127.0.0.1:8090/1.java
```
