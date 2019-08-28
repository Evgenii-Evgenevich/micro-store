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
```
curl -X POST "http://localhost:8000/api/product" -H "accept: application/json" -H "Content-Type: application/json" -d "{  \"characteristic\": { \"key1\":\"value\", \"key2\":\"value\", \"key3\":\"value\" }, \"description\": \"an awesome product\", \"title\": \"product\"}"
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
```
curl -X GET "http://localhost:8000/api/product/{id}" -H "accept: application/json"
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
