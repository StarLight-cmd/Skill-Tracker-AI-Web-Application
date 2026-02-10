import { useState } from "react";
import api from "../api/axios";
import Banner from "./Banner";

function ChatBot() {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const [loading, setLoading] = useState(false);

    const sendMessage = async () => {
        if (!input.trim()) return;

        setMessages(prev => [...prev, { role: "user", text: input }]);
        setLoading(true);

        try {
            const res = await api.post("/api/ai/chat", {
                message: input
            });

            setMessages(prev => [...prev, { role: "ai", text: res.data }]);
        } catch {
            setMessages(prev => [
                ...prev,
                { role: "ai", text: "AI service unavailable." }
            ]);
        }

        setLoading(false);
        setInput("");
    };

    return (
        <>
            <Banner />

            <div className="min-h-[calc(100vh-80px)] bg-gray-800 flex justify-center px-4 py-8">
                <div className="w-full max-w-3xl bg-white rounded-xl shadow flex flex-col">
                    
                    {/* Header */}
                    <div className="border-b px-6 py-4">
                        <h2 className="text-xl font-semibold text-gray-800">
                            SkillTracker AI
                        </h2>
                        <p className="text-sm text-gray-500">
                            Ask for learning plans, skill advice, or guidance
                        </p>
                    </div>

                    {/* Messages */}
                    <div className="flex-1 overflow-y-auto px-6 py-4 space-y-4">
                        {messages.map((m, i) => (
                            <div
                                key={i}
                                className={`flex ${m.role === "user" ? "justify-end" : "justify-start"}`}
                            >
                                <div
                                    className={`max-w-[75%] px-4 py-2 rounded-lg text-sm ${
                                        m.role === "user"
                                            ? "bg-blue-600 text-white rounded-br-none"
                                            : "bg-gray-200 text-gray-800 rounded-bl-none"
                                    }`}
                                >
                                    {m.text}
                                </div>
                            </div>
                        ))}

                        {loading && (
                            <p className="text-sm text-gray-400">
                                SkillTracker AI is thinking…
                            </p>
                        )}
                    </div>

                    {/* Input */}
                    <div className="border-t px-4 py-3 flex gap-2">
                        <input
                            value={input}
                            onChange={e => setInput(e.target.value)}
                            onKeyDown={e => e.key === "Enter" && sendMessage()}
                            placeholder="Ask about a skill or learning plan..."
                            className="flex-1 border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                        <button
                            onClick={sendMessage}
                            className="bg-blue-600 text-white px-6 rounded-lg hover:bg-blue-700 transition"
                        >
                            Send
                        </button>
                    </div>

                </div>
            </div>
        </>
    );
}

export default ChatBot;
