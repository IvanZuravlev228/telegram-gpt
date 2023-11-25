package ivan.telegramgpt.bot;

import ivan.telegramgpt.gpt.ChatGptClient;
import ivan.telegramgpt.model.entity.Chat;
import ivan.telegramgpt.model.entity.User;
import ivan.telegramgpt.service.ChatService;
import ivan.telegramgpt.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final ChatGptClient chatGptClient;
    private final UserService userService;
    private final ChatService chatService;

    @Value("${bot.name}")
    private String botName;
    private static final String START = "/start";

    public TelegramBot(@Value("${bot.token}") String botToken,
                       ChatGptClient chatGptClient,
                       UserService userService,
                       ChatService chatService) {
        super(botToken);
        this.chatGptClient = chatGptClient;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getChat().getUserName();
        if (userName == null) {
            noUserName(chatId);
            return;
        }
        if (message.equals(START)) {
            startCommand(chatId, userName);
            return;
        }
        if (userService.getByUsername(userName) == null) {
            noRegisteredUser(chatId);
            return;
        }
        String answer = chatGptClient.sendQuestion(message, chatId);
        sendMessage(chatId, answer);
        saveChat(message, answer, chatId);
    }

    private void noRegisteredUser(Long chatId) {
        String text = "You are not registered. Use /start for it";
        sendMessage(chatId, text);
    }

    private void noUserName(Long chatId) {
        String text = "You don't have a username, create one";
        sendMessage(chatId, text);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private void startCommand(Long chatId, String userName) {
        String text = "Hello %s. Glad to see you!\n"
                + "I'm a bot that uses chatGPT to solve your problems 0_0\n"
                + "Write to me what you would like to know?";
        if (!saveNewUser(chatId, userName)) {
            text = "You've already registered!";
        }
        String formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Error send message", e);
        }
    }

    private boolean saveNewUser(Long chatId, String userName) {
        if (userService.getByUsername(userName) != null) {
            return false;
        }
        User user = new User();
        user.setUsername(userName);
        user.setRole(User.Role.USER);
        user.setChatId(chatId);
        userService.save(user);
        return true;
    }

    public void saveChat(String userMessage, String gptAnswer, Long chatId) {
        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setUserMessage(userMessage);
        chat.setGptAnswer(gptAnswer);
        chatService.save(chat);
    }
}
