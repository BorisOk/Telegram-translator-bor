/** Класс определение языка. */
public class OnLangRuOrEn {
    String onLang(String text) {
        for (int i = 0; i < text.length(); i++) {
            char ch = text.trim().charAt(i);
            if ((ch >= 0x0041 && ch <= 0x005A) || (ch >= 0x0061 && ch <= 0x007A)) { // если текст на en
                return "ru"; // переводим на..
            }
            if ((ch >= 0x0410 && ch <= 0x044F) || ch == 0x0401 || ch == 0x0451) { // если текст на ru
                return "en"; // переводим на..
            }
        }
        throw new IllegalArgumentException("строка не (en) и не (ru) языка");
    }
}
