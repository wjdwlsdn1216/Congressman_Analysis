import torch
import cv2
import numpy as np
import argparse
import json

from efficientnet_pytorch import EfficientNet

parser = argparse.ArgumentParser()
parser.add_argument("--data", type=str, default='/works/Congressman_Analysis/python_api/data/val/5/12.jpg')
opt = parser.parse_args()

def load_json(json_path):
    with open(json_path,'r') as json_file:
        file = json.load(json_file)
        
    return file

congress_dict = load_json('/works/Congressman_Analysis/python_api/data/save_name.json')

data_dir = opt.data
weight_path = '/works/Congressman_Analysis/python_api/model/best_weights.pth'

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

model = EfficientNet.from_pretrained('efficientnet-b3', num_classes=297)
model.load_state_dict(torch.load(weight_path))
model.to(device)

model.eval()

inputs = cv2.imread(data_dir)
inputs = cv2.resize(inputs, (200,200))
ori_img = inputs.copy()
inputs = inputs[:, :, ::-1].transpose((2, 0, 1)).copy()
inputs = torch.from_numpy(inputs).float().div(255.0).unsqueeze(0)
inputs = inputs.cuda()

outputs = model(inputs)
_, preds = torch.max(outputs, 1)

cv2.putText(ori_img, str([k for k, v in congress_dict.items() if int(k) == preds.item()][0]),(75, 30), fontFace=cv2.FONT_HERSHEY_SIMPLEX, fontScale=1, color=(0,0,0))
cv2.imwrite('/works/Congressman_Analysis/python_api/result.jpg', ori_img)