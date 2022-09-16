from __future__ import print_function
import enchant
from itertools import *
import flask
from flask import Flask, jsonify, make_response, request
from flask_cors import CORS, cross_origin

app = Flask(__name__)

cors = CORS(app)
app.config['CORS_HEADERS'] = 'Access-Control-Allow-Origin'

ananas = "ananas"


# wordToCheck = ""


@app.route('/test', methods=['POST'])
def test():
    dictionary = enchant.request_pwl_dict("/home/daniel/Desktop/PyWo/demo/src/main/resources/static/js/index.txt")
    wordToCheck = str(request.data)[1:].replace("'", "")
    wordToCheck = wordToCheck.replace(" ", "")
    return str(dictionary.check(wordToCheck))


@app.route('/testAll', methods=['POST'])
def testAll():
    dictionary = enchant.request_pwl_dict("/home/daniel/Desktop/PyWo/demo/src/main/resources/static/js/index.txt")
    text = str(request.data)[1:].replace("'", "")
    text = text.split()
    values = []
    for x in text:
        values.append(str(dictionary.check(x)))
    return str(dict(zip(text, values)))


if __name__ == '__main__':
    # print(wordToCheck)
    app.run(host='localhost', port=8081)
