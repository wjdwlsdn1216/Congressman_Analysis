import requests

url = 'http://localhost/AI_API/장태정1'

res = requests.get(url)
json = res.json()
print(json)