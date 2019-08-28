# micro-store

-

    sudo mvn install dockerfile:build
-

    sudo docker run -p 8000:8000 micro-store
-

    curl -X POST "http://localhost:8000/api/product" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"characteristic\": {    \"key1\":\"value\",    \"key2\":\"value\",    \"key3\":\"value\"  },  \"description\": \"an awesome product\",  \"title\": \"product\"}"
-
