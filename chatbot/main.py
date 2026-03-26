from flask import Flask, request, jsonify
from flask_cors import CORS
from models.chat_model import ChatDB
from services.chatbot_service import get_bot_reply
from waitress import serve

app = Flask(__name__)
CORS(app)  # Allow React/Java backend to communicate
db = ChatDB()

@app.route("/")
def home():
    return jsonify({"status": "Chatbot is running"})

@app.route("/chat", methods=["POST"])
def chat():
    data = request.get_json()
    user_id = data.get("user_id", 1)
    message = data.get("message")

    if not message:
        return jsonify({"error": "Message is required"}), 400

    # Save user message
    db.save_chat(user_id, message, "USER")

    # Generate chatbot reply
    bot_reply = get_bot_reply(message)

    # Save bot response
    db.save_chat(user_id, bot_reply, "BOT")

    return jsonify({
        "user": message,
        "bot": bot_reply
    })

if __name__ == "__main__":
    print("🚀 Starting production WSGI server with Waitress...")
    serve(app, host="0.0.0.0", port=5001)