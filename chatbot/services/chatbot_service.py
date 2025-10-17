from transformers import pipeline

# Simple Hugging Face model for conversational responses
chatbot_pipeline = pipeline("text-generation", model="microsoft/DialoGPT-small")

def get_bot_reply(user_input):
    try:
        response = chatbot_pipeline(user_input, max_new_tokens=50, do_sample=True, temperature=0.7)
        return response[0]['generated_text']
    except Exception as e:
        print("Error generating response:", e)
        return "Sorry, I couldn't process that right now."
