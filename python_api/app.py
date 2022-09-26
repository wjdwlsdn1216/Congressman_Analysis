from flask import Flask

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False

@app.route('/AI_API/<name>')
def get(name):
    test = {
        "name":name,
        "result":0.01,
        "badkeyword":[
            "싫어",
            "싫다"
        ],
        "goodkeyword":[
            "좋다",
            "좋아"
        ]
    }
    return test

if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=80)