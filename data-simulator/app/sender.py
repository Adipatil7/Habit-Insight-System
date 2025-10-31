import requests
from config import BACKEND_URL

def data_sender(data):
    try:
        response = requests.post(BACKEND_URL , json=data)
        print(f"Sent: {data['machine_id']} | Status: {response.status_code}")
    except Exception as e:
        print("Error sending data: ",e) 
