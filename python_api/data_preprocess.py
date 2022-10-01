import os
import shutil
import argparse
from natsort import natsorted
import json
import random

def load_json(json_path):
    with open(json_path,'r') as json_file:
        file = json.load(json_file)
        
    return file

def file_list(file_path, ext='.jpg'):
    f_list =[]
    for each_file in os.listdir(file_path):
        file_ext = os.path.splitext(each_file)[1]

        if file_ext in [ext]:
            f_list.append(os.path.join(file_path, each_file))
    
    f_list = natsorted(f_list)

    return f_list

def save_name_json(image_folder):
    save_folder_name = [i.split('_') for i in image_folder]
    save_json = {i[0]:i[1] for i in save_folder_name}

    print(save_json['0'])
    with open('/works/Congressman_Analysis/python_api/data/save_name.json', 'w') as f:
        json.dump(save_json, f, indent='\t')

def rename_folder(base_path,image_folder):
    for num, folder in enumerate(image_folder):
        img_fold = "/".join(os.path.join(base_path, folder).split('/')[:-1])
        os.rename(os.path.join(base_path, folder), os.path.join(img_fold, str(num)))
        print(os.path.join(base_path, folder), os.path.join(img_fold, str(num)))

def rename_file(base_path,image_folder):
    for folder in image_folder:
        inner_fold = file_list(os.path.join(base_path, folder))
        for num, file in enumerate(inner_fold):
            rename = os.path.join(os.path.join(base_path, folder),f'{str(num+1)}.jpg')
            os.rename(file, rename)

def main():
    # base_path = '/works/Congressman_Analysis/python_api/data/train'
    # image_folder = natsorted(os.listdir(base_path))

    for number in range(134,297):

        src_dir = f'/works/Congressman_Analysis/python_api/data/train/{number}'
        target_dir = f'/works/Congressman_Analysis/python_api/data/val/{number}/'
        src_files = (os.listdir(src_dir))

        def valid_path(dir_path, filename):
            full_path = os.path.join(dir_path, filename)
            return os.path.isfile(full_path)


        files = [os.path.join(src_dir, f) for f in src_files if valid_path(src_dir, f)]

        try:
            choices = random.sample(files, 10)
        except:
            choices = random.sample(files, 1)

        if os.path.isdir(target_dir) == False:
            os.mkdir(target_dir)

        for files in choices:
            shutil.move(files, target_dir)
        print ('Finished!')

if __name__=="__main__":
    main()
        