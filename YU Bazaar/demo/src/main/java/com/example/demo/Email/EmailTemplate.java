package com.example.demo.Email;


public enum EmailTemplate {
    REGISTRATION_SUCCESS(
            "Welcome to Yu Bazaar – Registration Successful!",
            "Hi %s,\n\n" +
                    "Welcome to Yu Bazaar, the ultimate marketplace for York University students! 🎉\n\n" +
                    "Your registration was successful, and your account is now active.\n\n" +
                    "Here are some important details:\n" +
                    "- Recovery Code: %s\n" +
                    "  (Keep this code safe for password recovery if needed.)\n\n" +
                    "At Yu Bazaar, you can:\n" +
                    "- List items you want to sell or trade.\n" +
                    "- Browse and buy from a variety of items posted by fellow YorkU students.\n" +
                    "- Connect with your campus community in a safe and convenient way.\n\n" +
                    "We’re excited to have you join our growing marketplace!\n" +
                    "If you have any questions, feel free to reach out to us at yubazaarassistant@gmail.com.\n\n" +
                    "Let’s make buying and selling on campus easy, fun, and secure.\n\n" +
                    "Happy shopping and selling!\n" +
                    "The Yu Bazaar Team\n\n" +
                    "Support: yubazaarassistant@gmail.com"
    );

    private final String subject;
    private final String body;

    EmailTemplate(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }
// Getters
    public String getSubject() {
        return subject;
    }

    public String getBody(String... params) {
        return String.format(body, params);
    }
}
