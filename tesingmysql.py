import sys
import socketserver, unittest, threading
from datetime import time
from logging import exception

import mysql.connector
import socket, pickle
import re
import json
import struct
from threading import *

HOST = '192.168.1.6'
PORT = 8001
"""
class Test_service(unittest.TestCase):

    def setUp(self):
        client_socket = socket(socket.AF_INET, socket.SOCK_STREAM)
        client_socket.connect((HOST, PORT))

    def tearDown(self):
        client_socket.close()

    def test_1(self):
        client_socket.send('message1'.encode())
        self.assertEqual(client_socket.recv(1024).decode(), 'reply1')

    def test_2(self):
        client_socket.send('message2'.encode())
        self.assertEqual(client_socket.recv(1024).decode(), 'reply2')
"""
class Server:
    def __init__(self, host, port):
        self.host = host
        self.port = port

    def handle_client(self, client):
        # Server will just close the connection after it opens it
        client.close()
        return

    def start_listening(self):
        sock = socket.socket()
        sock.bind((self.host, self.port))
        sock.listen(5)
        client, addr = sock.accept()
        client.close()
    #def close_connection(self):


def mySQLConnect():
    mydb = mysql.connector.connect(
        host='localhost',
        user='root',
        passwd='M0mf0kas~',
        database='users'
    )
    return mydb

def mysqlCurssor(message):

    #insert_user = 'insert into users values (%s, %s, %s, %s)'
    #user = ("1", "grantas", "grantas@test.com", "grantas")
    select_all = 'select * from users'

    mydb = mySQLConnect()
    cursor = mydb.cursor()
    print(cursor)

    cursor.execute(message)
    show_all = cursor.fetchall()
    for all in show_all:

        print(all)


#TODO: make a email parser for security
#TODO: do the same for password
#TODO: verify the JSON by sending 0 pack

def getEmailCredentials(data):
    return re.findall(r'email=(\w+@\w+\.\w+)', data, re.S|re.U)

def getPassCredentials(data):
    return re.findall(r'password=([\w+,][^,]+)', data, re.S|re.U)

def getSqlCredentials(data):
    return re.findall(r'sql=(\w.[^}]*)', data, re.S|re.U)

def checkForAuth(data):
    mydb = mySQLConnect()
    cursor = mydb.cursor()
    flagger = False
    for i in getSqlCredentials(data):
        cursor.execute(i, (getEmailCredentials(data)[0], getPassCredentials(data)[0]))
        for all in cursor.fetchall():
            print('table', all)
        if cursor.rowcount > 0:
            print('Loggin In')
            flagger = True
    return flagger

def checkJsonValidation(data_in):
    try:
        return json.loads(data_in)
    except ValueError as e:
        print('invalid json: %s' % e)
        return None # or: raise

def loginCheckUp(data):
    sql_login = 'select * from users where email=%s and password=%s'
    mydb = mySQLConnect()
    cursor = mydb.cursor()
    #data_in = json.loads(data)
    data_in = checkJsonValidation(data)
    cursor.execute(sql_login, (data_in['Email'], data_in["Password"]))
    user_data = cursor.fetchall()
    # if user_data is not empty success message
    if user_data:
        return True
    else:
        return False

def sendDataToClient(data_in):
    if (loginCheckUp(data_in)):
        data_out ={}
        data_in = json.loads(data_in)
        data_out = data_in
        data_out['Entry'] = "LogIn"
        print('data out', data_out)
        #send the transformed json of data where shit says success
        #json.dumps(str(data_out))
    #else: send a message bad login

    #for all_data in data_in:
        send_response ={}
    #    send_response = all_data
        #send_response['name'] = all_data(1)
    #    print(send_response)
    #    print(all_data)
    #print(data_in)
    #print(data_in["Email"])
    return str(data_out)

def server_program(sql):

    serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    flag = False
    try:
        serversocket.bind((HOST, PORT))
    except socket.error as msg:
        print('Error has accured when binding with Code:', str(msg[0]), ' message: ', str(msg[1]))
        sys.exit()
    print('Binded')
    serversocket.listen(2)
    while True:
        conn, address = serversocket.accept() #accept new connection

        print('Connection from: ', str(address))
        serversocket.send(str( "siuncia" ).encode())
        #conn.sendall( str('Here I am!' ))
        data = conn.recv(4096).decode()
        #print(dict)

        #if checkForAuth(data) == True:
        #    payload = json.dumps({"success":True})
        #    conn.sendall(payload.encode())
        #Need to send data in this format
        """
            "email": email
            "password":password
            "entry": LogIn
        """
        #print( loginCheckUp( data ) )
        # sendDataToClient(data)
        # data_out = sendDataToClient(data)

        if (data == sql):
            mysqlCurssor( data )
            print( "Hi there" )
        else:
            print( 'Goodbye~' )

        #if (loginCheckUp(data) == True):
            #send data
        #    print(loginCheckUp(data))
        #else:
            #close connection
        #    conn.close()

            #conn.close()  # close the connection
            #break
    #conn.close()  # close the connection
    serversocket.serve_forever()


def runTests():
    serverTest = Server( HOST, PORT )
    serverTest.start_listening()

    print('TEST COMPLETED')


def main():
    sql = 'select * from users'
    #mysqlCurssor(sql)
    runTests()
    #serverTest.handle_client()
    #server_program(sql)


if __name__ == '__main__':
    sql = 'select * from users'
    main()
    #mysqlCurssor()
    server_program(sql)

    """  
    while True:
        # receive data stream. it won't accept data packet greater than 1024 bytes
        data = conn.recv(1024).decode()
        if not data:
            # if data is not received break
            break
        print("from connected user: " + str(data))
        data = input(' -> ')
        conn.send(data.encode())  # send data to the client
    """