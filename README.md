# micro-store

## Init
- build docker image 
```
sudo mvn install dockerfile:build
```
- run docker image 
```
sudo docker run -p 8000:8000 micro-store
```
## RestAPI
- Create a new product // Создать новый товар
  - `POST: http://localhost:8000/api/product`
```bash
curl -X POST "http://localhost:8000/api/product" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"characteristic\": { \"key1\":\"value\", \"key2\":\"value\", \"key3\":\"value\" }, \"description\": \"an awesome product\", \"title\": \"product\"}"
```
Response body
```json
{
  "id": "5d663c8786fc7828cc13bf75",
  "dateTime": "2019-08-28T08:34:15",
  "title": "product",
  "description": "an awesome product",
  "characteristic": {
    "key1": "value",
    "key2": "value",
    "key3": "value"
  }
}
```
- Get details of a pruduct by id // Получить детали товара по ID
  - `GET: http://localhost:8000/api/product/{id}`
```bash
curl -X GET "http://localhost:8000/api/product/5d663c8786fc7828cc13bf75" -H "accept: application/json"
```
Response body
```json
{
  "id": "5d663c8786fc7828cc13bf75",
  "dateTime": "2019-08-28T08:34:15",
  "title": "product",
  "description": "an awesome product",
  "characteristic": {
    "key1": "value",
    "key2": "value",
    "key3": "value"
  }
}
```
- Update a pruduct by id
  - `POST: http://localhost:8000/api/product/{id}`
```bash
curl -X POST "http://localhost:8000/api/product/5d663c8786fc7828cc13bf75" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"characteristic\": { \"key1\":\"value1\", \"key2\":\"value2\", \"key3\":\"value3\" }, \"description\": \"an awesome product\", \"title\": \"product\" }"
```
Response body
```json
{
  "id": "5d663c8786fc7828cc13bf75",
  "dateTime": "2019-08-28T08:34:15",
  "title": "product",
  "description": "an awesome product",
  "characteristic": {
    "key1": "value1",
    "key2": "value2",
    "key3": "value3"
  }
}
```
- Get a page of pruducts
  - `GET: http://localhost:8000/api/product/{page}/{count}`
```bash
curl -X GET "http://localhost:8000/api/product/0/5" -H "accept: application/json"
```
Response body
```json
{
  "content": [
    {
      "id": "5d663c8786fc7828cc13bf75",
      "title": "product",
      "description": "an awesome product"
    }, {
      "id": "5d6645dc86fc7828cc13bf76",
      "title": "product",
      "description": "a product 1"
    }, {
      "id": 5d6645df86fc7828cc13bf77",
      "title": "product",
      "description": "a product 2"
    }, {
		"id": "5d6645e486fc7828cc13bf78",
      "title": "product",
      "description": "a product 3"
    }, {
      "id": "5d6645ec86fc7828cc13bf79",
      "title": "product",
      "description": "a product 3"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 5,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "totalElements": 10,
  "totalPages": 2,
  "size": 5,
  "number": 0,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 5,
  "first": true,
  "empty": false
}
```

