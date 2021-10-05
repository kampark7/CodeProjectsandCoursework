from flask import jsonify, Flask, request
import urllib.parse
import joblib

app = Flask(__name__)

rand_forest = joblib.load("rand_forest.pk1")

@app.route("/", methods = ["GET"])
def reciever():
    event = request.get_json()
    data = [[
        int(event["WAR/pos"]),
        int(event["G"]),
        int(event["PA"]),
        int(event["AB"]),
        int(event["R"]),
        int(event["H"]),
        int(event["2B"]),
        int(event["HR"]),
        int(event["RBI"]),
        int(event["CS"]),
        int(event["BB"]),
        int(event["SO"]),
        int(event["BA"]),
        int(event["OBP"]),
        int(event["SLG"]),
        int(event["OPS"]),
        int(event["Age"])
    ]]
    pred = rand_forest.predict(data)[0]

    return jsonify(str(pred))

if __name__ == "__main__":
    app.run()