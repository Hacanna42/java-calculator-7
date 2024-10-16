package calculator;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;

public class Application {
    private static final String DELIMITER_COMMA = ",";
    private static final String DELIMITER_DOT = ".";
    private static final String CUSTOM_DELIMITER_START = "//";
    private static final String CUSTOM_DELIMITER_END = "\\n";

    // 구분자를 저장하기 위한 ArrayList
    static ArrayList<String> delimiters = new ArrayList<>();

    public static void main(String[] args) {
        // 사용자로부터 문자열을 입력받음
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();

        // 기본 구분자를 ArrayList 에 추가
        delimiters.add(DELIMITER_COMMA);
        delimiters.add(DELIMITER_DOT);

        if (input.startsWith(CUSTOM_DELIMITER_START)) { // 커스텀 구분자가 지정되어 있는지 확인 후, 지정되어 있다면 커스텀 구분자를 ArrayList 에 추가
            String newDelimiter = getCustomDelimiter(input);
            delimiters.add(newDelimiter);
        }

        // 문자열에서 숫자 배열을 추출 후, 총합 계산
        ArrayList<Integer> numbers = convertStringToIntArray(input);
        int sum = sumArray(numbers);
        System.out.println("결과 : " + sum);
    }

    static String getCustomDelimiter(String input) { // 문자열에서 커스텀 구분자를 추출하고 반환하는 함수
        int endIndex = input.indexOf(CUSTOM_DELIMITER_END);
        if (endIndex == -1) { // 찾지 못했을 경우 입력 값 오류이므로 IllegalArgumentException
            throw new IllegalArgumentException();
        }

        String delimiter = input.substring(2, endIndex);
        if (delimiter.length() != 1) { // 구분자의 길이가 1이 아닌 경우 입력 값 오류이므로 IllegalArgumentException. 구분자의 길이가 0인 경우도 포함
            throw new IllegalArgumentException();
        }

        if (Character.isDigit(delimiter.charAt(0))) { // 구분자가 숫자인 경우 입력 값 오류이므로 IllegalArgumentException
            throw new IllegalArgumentException();
        }

        return delimiter;
    }

    static boolean isValidDelimiter(char input) { // 주어진 문자가 올바른 구분자인지 확인하는 함수
        for (String delimiter : delimiters) {
            if (input == delimiter.charAt(0)) {
                return true;
            }
        }

        return false;
    }

    static ArrayList<Integer> convertStringToIntArray(String input) {
        int index = 0;
        if (input.startsWith(CUSTOM_DELIMITER_START)) { // 구분자가 있다면 인덱스를 구분자 다음으로 조정
            index = input.indexOf(CUSTOM_DELIMITER_END) + 2;
        }

        ArrayList<Integer> numbers = new ArrayList<>(); // 숫자 배열을 저장하는 ArrayList
        StringBuilder currentNumber = new StringBuilder(); // 현재 숫자를 임시로 저장하는 StringBuilder

        // 엣지 케이스: 아무 숫자도 입력되지 않았을 경우 처리 (커스텀 구분자가 지정되었을 경우에도)
        if (input.isEmpty() || input.length() == index) {
            return numbers;
        }

        for (int i = index; i < input.length(); i++) {
            char currentChar = input.charAt(i); // 현재 인덱스의 문자

            if (Character.isDigit(currentChar)) { // 현재 문자가 숫자일 경우, currentNumber(버퍼) 에 추가
                currentNumber.append(currentChar);
            } else if (isValidDelimiter(currentChar) && !currentNumber.isEmpty()) { // 현재 문자가 구분자고, 버퍼가 비어있지 않다면
                int convertedNumber = Integer.parseInt(currentNumber.toString());
                numbers.add(convertedNumber);
                currentNumber.setLength(0);
            } else { // 숫자도 구분자도 아니거나, 숫자 이전에 구분자가 나온 경우 잘못된 값이므로 IllegalArgumentException
                throw new IllegalArgumentException();
            }
        }

        // loop 종료 이후 버퍼에 남은 마지막 숫자 처리
        if (!currentNumber.isEmpty()) {
            numbers.add(Integer.parseInt(currentNumber.toString()));
        } else { // currentNumber 가 비어있다면, 구분자 이후에 숫자를 입력하지 않았으므로 IllegalArgumentException
            throw new IllegalArgumentException();
        }

        return numbers;
    }

    static int sumArray(ArrayList<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }

        return sum;
    }
}
