from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
import pandas as pd
import sys

def main():
    uri = "mongodb+srv://user:<PASSAWORD>@techhorizon.dkxdv.mongodb.net/?retryWrites=true&w=majority&appName=TechHorizon"    # Create a new client and connect to the server
    client = MongoClient(uri, server_api=ServerApi('1'))
    db = client.nosqllocaltracker
    my_collection = db['LocalTracker']


    # Send a ping to confirm a successful connection
    try:
        client.admin.command('ping')
        print("Pinged your deployment. You successfully connected to MongoDB!")
    except Exception as e:
        print(e)

    db = client



    file_xlsx = 'Fatec_locations_(Metadata)_(2).xlsx'
    file_txt = 'Fatec_locations.txt'
    users_list = get_file_xlsx(file_xlsx)
    locations = get_file_txt(file_txt)
    locations_list = populate_positions(locations, users_list)
    # insert_data(my_collection, locations_list, client)
    search_data(my_collection)

def populate_positions(locations, users_list):
    locations_list = []
    for loc in locations:
        loc = loc.split("|")
        user = search_user(loc[5], users_list)

        document = {
            "Id": loc[0],
            "CreatedAt": loc[1],
            "lat": loc[2],
            "long": loc[3],
            "fullName": loc[4],
            "code": loc[5],
            "location": loc[6],
            "macAddress": user['MacAddress'],
            "codeDevice": user['CodeDevice'],
            "idDevice": user['Id'],
        }
        locations_list.append(document)
    return locations_list

def insert_data(my_collection, locations_list, client):
    try: 
        result = my_collection.insert_many(locations_list)

    except client.errors.OperationFailure:
        print("An authentication error was received. Are you sure your database user is authorized to perform write operations?")
        sys.exit(1)
    else:
        inserted_count = len(result.inserted_ids)
        print("I inserted %x documents." %(inserted_count))
        print("\n")

def search_data(my_collection):
    my_doc = my_collection.find_one({"fullName": "Darwin Yoel Franco Vasquez"})
    print(my_doc)

def get_file_xlsx(file_name):
    users = pd.read_excel(file_name, engine='openpyxl')
    users_list = users.to_dict(orient='records')
    return users_list

def get_file_txt(file_name):
    file = open(file_name, "r")
    locations = file.read().split("\n")[1:]
    return locations

def search_user(code, users_list):
    empty = {'Id': '', 'CodeDevice': '', 'MacAddress': '', 'Fullname': '', 'Code': ''}
    for user in users_list:
        if str(user["Code"]) == str(code):
            return user
    return empty


main()







{   
    '_id': ObjectId('66e3a0cf9754d54c60f61d5d'),
     'Id': '{CD5F43AE-EAF6-4539-ADF4-000060C93ADC}',
     'CreatedAt': '2024-02-14 18:11:57.903000000',
     'lat': '2,8332661327',
     'long': '-60,6893421190',
     'fullName': 'Darwin Yoel Franco Vasquez',
     'code': '1435',
     'location': '',
     'macAddress': 'AC:23:3F:AB:9C:9A',
     'codeDevice': 'Card_9C9A',
     'idDevice': '1640966C-BBAC-4A26-8A06-0670296D361C'
}