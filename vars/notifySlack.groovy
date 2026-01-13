def call(String channel, String message, String status) {
    def emoji = status == "SUCCESS" ? "✅" : "❌"
    echo "Slack → ${channel}: ${emoji} ${message} (${status})"
    // slackSend channel: channel, message: "${emoji} ${message} (${status})"
}
