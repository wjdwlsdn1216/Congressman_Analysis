import requests
import json

url = 'https://70b6-221-156-19-39.jp.ngrok.io/predict'

res = requests.post(url, files={"file":open('/works/Congressman_Analysis/python_api/data/train/0/1.jpg','rb')})

print(json.loads(res.content))