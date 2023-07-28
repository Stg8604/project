from fastapi import FastAPI, Depends,  HTTPException, status,Form
from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm
from pydantic import BaseModel
from typing import Annotated
from datetime import datetime,timedelta
#from jose import JWTError,jwt
import jwt
from jwt.exceptions import InvalidTokenError
from passlib.context import CryptContext
import mysql.connector
from mysql.connector import connect,Error
mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  password="Stg08604",
  database="project",
   port='3306'
)
cursor=mydb.cursor()
selectstatement="select * from users"
cursor.execute(selectstatement)
result=cursor.fetchall()
cursor.close()
app=FastAPI()
@app.get("/users/me/")
async def read_users():
    cursor=mydb.cursor()
    selectstatement="select username from users"
    cursor.execute(selectstatement)
    result=cursor.fetchall()
    cursor.close()
    pb=list()
    for i in result:
        pb.append(i[0])
    return pb
@app.post("/users/me/days")
async def read_users(username:Annotated[str,Form()],day:Annotated[str,Form()]):
    cursor=mydb.cursor()
    selectstatement="select task,timestart,timeend,sidenote from days where username=%s and day=%s"
    values=(username,day)
    cursor.execute(selectstatement,values)
    result=cursor.fetchall()
    cursor.close()
    pb=list()
    for i in result:
        pb.append({"task":i[0],"timestart":i[1],"timeend":i[2],"sidenote":i[3]})
    return pb
@app.post("/users/me/items")
async def read_users(username:Annotated[str,Form()],day:Annotated[str,Form()],task:Annotated[str,Form()]):
    cursor=mydb.cursor()
    tablename=day.lower()+"items"
    selectstatement="SELECT * FROM {} WHERE task = %s AND username = %s".format(tablename)
    values=(task,username)
    cursor.execute(selectstatement,values)
    result=cursor.fetchall()
    cursor.close()
    pb=list()
    for i in result:
        pb.append({"task":task,"item":i[2],"selects":i[3]})
    return pb
@app.post("/users/me/schedule")
async def read_users(username:Annotated[str,Form()],day:Annotated[str,Form()],task:Annotated[str,Form()]):
    cursor=mydb.cursor()
    tablename=day.lower()+"schedule"
    selectstatement="select * from {} where task=%s and username=%s".format(tablename)
    values=(task,username)
    cursor.execute(selectstatement,values)
    result=cursor.fetchall()
    cursor.close()
    pb=list()
    for i in result:
        pb.append({"task":task,"slot":i[1],"timestart":i[2],"timeend":i[3],"info":i[4],"done":i[5]})
    return pb
@app.post("/users/me/addnotes")
async def read_users(username:Annotated[str,Form()],day:Annotated[str,Form()],task:Annotated[str,Form()],note:Annotated[str,Form()]):
    cursor=mydb.cursor()
    tablename=day.lower()+"notes"
    selectstatement="insert into {} values(%s,%s,%s)".format(tablename)
    values=(username,task,note)
    cursor.execute(selectstatement,values)
    cursor.close()
    mydb.commit()
@app.post("/users/me/notes")
async def read_users(username:Annotated[str,Form()],day:Annotated[str,Form()],task:Annotated[str,Form()]):
    tablename=day.lower()+"notes"
    cursor=mydb.cursor()
    selectstatement="select * from {} where username=%s and task=%s".format(tablename)
    values=(username,task)
    cursor.execute(selectstatement,values)
    result=cursor.fetchall()
    cursor.close()
    pb=list()
    for i in result:
        pb.append({"task":task,"note":i[2]})
    return pb
@app.post("/users/me/sidenote")
async def read_users(username:Annotated[str,Form()],day:Annotated[str,Form()],task:Annotated[str,Form()],sidenote:Annotated[str,Form()]):
    tablename=day.lower()+"notes"
    cursor=mydb.cursor()
    selectstatement="update days set sidenote=%s where username=%s and task=%s and day=%s"
    values=(sidenote,username,task,day)
    cursor.execute(selectstatement,values)
    cursor.close()
    mydb.commit()
@app.post("/users/me/select")
async def read_users(username:Annotated[str,Form()],day:Annotated[str,Form()],task:Annotated[str,Form()],selects:Annotated[str,Form()],item:Annotated[str,Form()]):
    tablename=day.lower()+"items"
    cursor=mydb.cursor()
    selectstatement="update {} set selects=%s where username=%s and task=%s and item=%s".format(tablename)
    values=(selects,username,task,item)
    cursor.execute(selectstatement,values)
    cursor.close()
    mydb.commit()
@app.post("/users/me/done")
async def read_users(username:Annotated[str,Form()],day:Annotated[str,Form()],task:Annotated[str,Form()],done:Annotated[str,Form()],slot:Annotated[str,Form()]):
    tablename=day.lower()+"schedule"
    cursor=mydb.cursor()
    selectstatement="update {} set done=%s where username=%s and task=%s and slot=%s".format(tablename)
    values=(done,username,task,slot)
    cursor.execute(selectstatement,values)
    cursor.close()
    mydb.commit()
@app.post("/users/me/sptasks")
async def read_users(username:Annotated[str,Form()],task:Annotated[str,Form()],slot:Annotated[str,Form()]):
    cursor=mydb.cursor()
    selectstatement="insert into sptasks values(%s,%s,%s)"
    values=(username,task,slot)
    cursor.execute(selectstatement,values)
    cursor.close()
    mydb.commit()
@app.post("/users/me/getsptasks")
async def read_users(username:Annotated[str,Form()]):
    cursor=mydb.cursor()
    selectstatement="select * from sptasks where username=%s"
    values=(username,)
    cursor.execute(selectstatement,values)
    result=cursor.fetchall()
    pb=list()
    for i in result:
        pb.append({"task":i[1],"slot":i[2]})
    cursor.close()
    mydb.commit()
    return pb
@app.post("/users/me/spitems")
async def read_users(username:Annotated[str,Form()],task:Annotated[str,Form()],item:Annotated[str,Form()]):
    cursor=mydb.cursor()
    selectstatement="insert into spitems values(%s,%s,%s,%s)"
    values=(username,task,item,"select")
    cursor.execute(selectstatement,values)
    cursor.close()
    mydb.commit()
@app.post("/users/me/spitemsselect")
async def read_users(username:Annotated[str,Form()],task:Annotated[str,Form()],item:Annotated[str,Form()],select:Annotated[str,Form()]):
    cursor=mydb.cursor()
    selectstatement="update spitems set selects=%s where username=%s and task=%s and items=%s"
    values=(select,username,task,item)
    cursor.execute(selectstatement,values)
    cursor.close()
    mydb.commit()
@app.post("/users/me/getspitems")
async def read_users(username:Annotated[str,Form()],task:Annotated[str,Form()]):
    cursor=mydb.cursor()
    selectstatement="select * from spitems where username=%s and task=%s"
    values=(username,task)
    cursor.execute(selectstatement,values)
    result=cursor.fetchall()
    pb=list()
    for i in result:
        pb.append({"item":i[2],"select":i[3]})
    cursor.close()
    mydb.commit()
    return pb
@app.post("/users/me/spnotes")
async def read_users(username:Annotated[str,Form()],task:Annotated[str,Form()],note:Annotated[str,Form()]):
    cursor=mydb.cursor()
    selectstatement="insert into spnotes values(%s,%s,%s)"
    values=(username,task,note)
    cursor.execute(selectstatement,values)
    cursor.close()
    mydb.commit()
@app.post("/users/me/getspnotes")
async def read_users(username:Annotated[str,Form()],task:Annotated[str,Form()]):
    cursor=mydb.cursor()
    selectstatement="select * from spnotes where username=%s and task=%s"
    values=(username,task)
    cursor.execute(selectstatement,values)
    result=cursor.fetchall()
    pb=list()
    for i in result:
        pb.append({"note":i[2]})
    cursor.close()
    mydb.commit()
    return pb

