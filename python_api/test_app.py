import requests
import json

url = 'http://192.168.0.49:80/predict'

res = requests.post(url, files={"file":open('/works/Congressman_Analysis/python_api/data/train/0/1.jpg','rb')})

print(json.loads(res.content))