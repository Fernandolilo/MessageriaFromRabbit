 #pip install selenium
import requests
import time

category = { 
    "category": "informatica"
}

product = {
  "name":"Pen Driver",
  "price": 50.00,
  "amount": 50,
  "category": {"id": 1}
}



for i in range(1000):
  post_response = requests.post(url = 'http://localhost:8000/estoque/categories', json=category)

time.sleep(2)

for i in range(1000):
  post_response = requests.post(url = 'http://localhost:8000/estoque/products', json=product)





   
   
