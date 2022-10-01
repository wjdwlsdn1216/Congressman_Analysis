from flask import Flask, request, jsonify
import torch
import cv2
import numpy as np
import json

from efficientnet_pytorch import EfficientNet

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False

def load_json(json_path):
    with open(json_path,'r') as json_file:
        file = json.load(json_file)
        
    return file

def prediction(data):
    weight_path = '/works/Congressman_Analysis/python_api/model/best_weights.pth'

    device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

    model = EfficientNet.from_pretrained('efficientnet-b3', num_classes=297)
    model.load_state_dict(torch.load(weight_path))
    model.to(device)

    model.eval()

    inputs = cv2.resize(data, (200,200))
    ori_img = inputs.copy()
    inputs = inputs[:, :, ::-1].transpose((2, 0, 1)).copy()
    inputs = torch.from_numpy(inputs).float().div(255.0).unsqueeze(0)
    inputs = inputs.cuda()

    outputs = model(inputs)
    _, preds = torch.max(outputs, 1)
    print(_)
    print(preds)
    congress_dict = load_json('/works/Congressman_Analysis/python_api/data/save_name.json')

    class_name = str(congress_dict[str(preds.item())])

    return _.item(), class_name

@app.route('/predict', methods=['POST'])
def get():
    file = request.files['file'].read()
    print(type(file))
    file_bytes = np.fromstring(file, np.uint8)
    image = cv2.imdecode(file_bytes, cv2.IMREAD_UNCHANGED)
    result, class_name = prediction(image)
    
    return jsonify({'result':round(result*10,2), 'class_name':class_name})

if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=80)