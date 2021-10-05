import requests
import json

query = {
        "WAR/pos": 1.7,
        "G" : 317,
        "PA": 317,
        "AB": 277,
        "R": 47,
        "H": 73,
        "2B": 8,
        "HR": 17,
        "RBI": 14,
        "CS" : 1,
        "BB": 37,
        "SO": 100,
        "BA": 0.264,
        "OBP": 0.347,
        "SLG": 0.477,
        "OPS": 0.824,
        "Age": 24
        }

prediction = requests.get("http://127.0.0.1:5000", json = query)
print(prediction.json())