import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import static com.darkprograms.speech.translator.GoogleTranslate.translate;

/** Класс-обработчик поступающих к боту сообщений. */

public class EchoBot extends TelegramLongPollingBot {

    private final OnLangRuOrEn ruOrEn = new OnLangRuOrEn();
    /** Метод, который возвращает токен, выданный нам ботом @BotFather. */
    @Override
    public String getBotToken() {
        return "1409363246:AAGxqsJG0wTaO3t_JCFT0UGHf_75JKUAf48";
    }

    /** Метод, который возвращает имя бота. */
    @Override
    public String getBotUsername() {
        return "Test3964TranslatorBot";
    }

    /** Метод-обработчик поступающих сообщений. update объект, содержащий информацию о входящем сообщении. */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            //проверяем есть ли сообщение и текстовое ли оно
            if (update.hasMessage() && update.getMessage().hasText()) {
                //Извлекаем объект входящего сообщения
                Message inMessage = update.getMessage();
                //Создаем исходящее сообщение
                SendMessage outMessage = new SendMessage();
                //Указываем в какой чат будем отправлять сообщение 
                //(в тот же чат, откуда пришло входящее сообщение)
                outMessage.setChatId(inMessage.getChatId());
                //Указываем текст сообщения и изменяем его
                String text = inMessage.getText();
                String translatedText = translatedMessage(text);
                outMessage.setText(translatedText);
                //Отправляем сообщение
                execute(outMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /** Метод переводит и собирает полностью сообщение. */
    String translatedMessage(String text) {
        String language = ruOrEn.onLang(text);
        if (text.equals("/start")) {
            return "Welcome";
        }
        String prefix = textPrefix(language);
        String textTranslate = null;

        try { textTranslate = translate(language,text);
        } catch (IOException e) { e.printStackTrace(); }
        return prefix + textTranslate;
    }

    /** Метод создает приставку. */
    private String textPrefix(String language) {
        if (language.equals("ru")) {
            return "* Перевод: \n";
        } else {
            return "* Translate: \n";
        }
    }
}