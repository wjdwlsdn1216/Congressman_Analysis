from flask import Flask

app = Flask(__name__)

@app.route('/AI_API/<name>')
def get(name):
    test = {
        "name":name,
        "result":0.01
    }
    return test

if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=80)