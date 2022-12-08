insert records

POST: http://localhost:8081/api/meterReadings
-H "Content-Type:application/json"
```json
[
  {
   "siteId": 1,
   "dateTime": "2020-01-01T00:00Z[UTC]",
   "whUsed": 2.5,
   "whGenerated": 3.0,
   "tempC": 22.5
  },
  {
   "siteId": 1,
   "dateTime": "2020-01-01T00:01Z[UTC]",
   "whUsed": 2.4,
   "whGenerated": 3.1,
   "tempC": 22.0
  }
]
```
