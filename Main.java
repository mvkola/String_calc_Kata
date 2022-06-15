import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        System.out.println("Введи выражение: аргумент-1 (строго строка) ЗНАК аргумент-2 (строка или число).");
        System.out.println("Для строк используй двойные кавычки и длину не более 10 значений.");
        System.out.println("Если строку надо умножить или разделить, то после знака м.б. только числа от 1 до 10.");

        Scanner console = new Scanner(System.in);
        String textInput = console.nextLine();
        char operation = 0;

        if (textInput.contains(Sign.PLUS.signSpace)) {        // условие: используйте принципы ООП
            operation = Sign.PLUS.getSign();                  // постарайтесь разбить программу на логические классы
        }
        if (textInput.contains(Sign.MINUS.signSpace)) {
            operation = Sign.MINUS.getSign();
        }
        if (textInput.contains((Sign.MUL.signSpace))) {
            operation = Sign.MUL.getSign();
        }
        if (textInput.contains((Sign.DIV.signSpace))) {
            operation = Sign.DIV.getSign();
        }

        int findIndex = textInput.indexOf(" " + operation + " ");     // ищем индекс первого вхождения str signSpace
        int operationIndex = findIndex + 1;                               // определяем индекс математического знака

        StringBuilder text = new StringBuilder();            // создаём объект класса StringBuilder для трансформации
        text.append(textInput);

        StringBuilder arg1 = new StringBuilder(text.substring(0, operationIndex)); // берём под-строку ДО индекса знака

        if ((arg1.charAt(0) != '"') || (arg1.charAt(operationIndex - 2)) != '"') {
            try {                                               // проверяем наличие кавычек у аргумента-1 (условие)
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Ошибка, т.к. формат ввода арг-1 не соответствует условию (кавычки или число)");
                return;
            }
        }
        arg1.deleteCharAt(0);                                      // удаляем кавычки и лишний пробел
        arg1.deleteCharAt(arg1.length() - 1);
        arg1.deleteCharAt(arg1.length() - 1);

        if (arg1.length() > 10) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Ошибка, т.к. формат ввода арг-1 не соответствует условию (длина строки)");
                return;
            }
        }

        String arg = textInput.substring(operationIndex + 2);  // извлекаем под-строку ПОСЛЕ индекса знака

        if ((operation == '*') || (operation == '/')) {            // проверяем, стоит ли после нужного знака число?
            try {
                int count = Integer.parseInt(arg);
                if ((count < 1) || (count > 10)) {                 // проверяем диапазон числа
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("Ошибка, т.к. формат ввода арг-2 не соответствует условию (диапазон числа)");
                        return;
                    }
                }

               calcInt(arg1, count, operation);
                return;

            } catch (NumberFormatException e) {
                throw new RuntimeException("Ошибка, т.к. формат ввода не соответствует условию");
            }
        }
        if ((text.charAt(operationIndex + 2) != '"') || (text.charAt(text.length() - 1)) != '"') {
            try {                                        // проверяем наличие кавычек у аргумента-2 (условие для строк)
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Ошибка, т.к. формат ввода арг-2 не соответствует условию (кавычки или знак)");
                return;
            }
        }
        StringBuilder arg2 = new StringBuilder(text.substring(operationIndex + 2));
        arg2.deleteCharAt(0);                                          // берём под-строку ПОСЛЕ знака
        arg2.deleteCharAt(arg2.length() - 1);                          // создаём объект, убираем кавычки

        calcStr(arg1, arg2, operation);

        if (arg2.length() > 10) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Ошибка, т.к. формат ввода арг-2 не соответствует условию (длина строки)");
            }
        }
    }

    private static void calcStr(StringBuilder arg1, StringBuilder arg2, char operation) {
        StringBuilder resultStr = new StringBuilder();
        switch (operation) {
            case '+':
                resultStr = arg1.append(arg2);                              // соединяем две строки
                break;
            case '-':
                try {
                    int startIndex = arg1.indexOf(String.valueOf(arg2));    // находим индекс вхождения арг-2 в арг-1
                    if (startIndex == -1) {                    // если нет вхождения, удаляем исходную строку (арг-2)
                        resultStr = arg1;
                    } else {
                        if (startIndex > 0) {                          // если есть вхождение, удаляем все подстроки
                            Pattern pattern = Pattern.compile(String.valueOf(arg2));
                            Matcher matcher = pattern.matcher(arg1);

                            while (matcher.find()) {
                                resultStr = new StringBuilder(matcher.replaceAll(""));
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                break;
            default:
        }
        resultStr.insert(0, '"').append('"');                    // добавляем кавычки для вывода результата
        System.out.println(resultStr);
    }

    private static void calcInt(StringBuilder arg1, int count, char operation) {
        StringBuilder resultInt = new StringBuilder();
        switch (operation) {
            case '*':
                if (count == 1) {
                    resultInt = arg1;                        // если единица, то повторяем арг-1 (можно это опустить)
                } else {
                    resultInt = new StringBuilder(count * arg1.length());        // задаём вместимость строки
                    resultInt.append(String.valueOf(arg1).repeat(Math.max(0, count)));  // повторяем арг-1 кол-во раз
                }
                if ((resultInt.length() > 40)) {
                    resultInt.append("...");            // по условию если > 40 символов, то ставим три точки в конце
                }
                break;
            case '/':
                int len = arg1.length();                              // узнаём кол-во символов арг-1
                int divide = len / count;                             // делим кол-во символов
                resultInt = arg1.delete(divide, len);                 // оставляем только нужное количество символов
                break;
            default:
        }
        resultInt.insert(0, '"').append('"');                // добавляем кавычки для вывода результата
        System.out.println(resultInt);
    }
}