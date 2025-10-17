import mysql.connector
from mysql.connector import Error
from datetime import datetime
from config import Config

class ChatDB:
    def __init__(self):
        try:
            self.connection = mysql.connector.connect(
                host=Config.MYSQL_HOST,
                user=Config.MYSQL_USER,
                password=Config.MYSQL_PASSWORD,
                database=Config.MYSQL_DB
            )
            if self.connection.is_connected():
                print("✅ MySQL connected successfully.")
        except Error as e:
            print("❌ MySQL connection error:", e)

    def save_chat(self, user_id, message, sender):
        cursor = self.connection.cursor()
        query = "INSERT INTO chat_history (user_id, message, sender, timestamp) VALUES (%s, %s, %s, %s)"
        cursor.execute(query, (user_id, message, sender, datetime.now()))
        self.connection.commit()
        cursor.close()
