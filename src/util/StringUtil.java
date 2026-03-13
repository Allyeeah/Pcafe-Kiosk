package util;

public class StringUtil {

    public static String padRightByWidth(String text, int targetWidth) {
        if (text == null) text = "-";

        int currentWidth = getDisplayWidth(text);
        StringBuilder sb = new StringBuilder(text);

        while (currentWidth < targetWidth) {
            sb.append(' ');
            currentWidth++;
        }

        return sb.toString();
    }

    public static String trimTextByWidth(String text, int maxWidth) {
        if (text == null) return "-";

        int width = 0;
        StringBuilder sb = new StringBuilder();

        for (char ch : text.toCharArray()) {
            int charWidth = isKorean(ch) ? 2 : 1;

            if (width + charWidth > maxWidth - 1) {
                sb.append('…');
                return sb.toString();
            }

            sb.append(ch);
            width += charWidth;
        }

        return sb.toString();
    }

    private static int getDisplayWidth(String str) {
        if (str == null || str.isEmpty()) return 0;

        int width = 0;
        for (char ch : str.toCharArray()) {
            width += isKorean(ch) ? 2 : 1;
        }
        return width;
    }

    private static boolean isKorean(char ch) {
        return (ch >= '가' && ch <= '힣')   // 한글 완성형
                || (ch >= 'ᄀ' && ch <= 'ᇿ') // 한글 자모
                || (ch >= '\u3130' && ch <= '\u318F'); // 호환 자모
    }
}
