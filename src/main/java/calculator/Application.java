package calculator;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;

public class Application {
    // 구분자를 저장하기 위한 ArrayList
    static ArrayList<String> delimiters = new ArrayList<>();

    public static void main(String[] args) {
        // 사용자로부터 문자열을 입력받음
        String input = Console.readLine();

        // 기본 구분자를 ArrayList 에 추가
        delimiters.add(",");
        delimiters.add(":");

        // 커스텀 구분자가 지정되어 있는지 확인 후, 지정되어 있다면 커스텀 구분자를 ArrayList 에 추가
        if (hasCustomDelimiter(input)) {
            delimiters.add(getCustomDelimiter(input));
        }

        // convertStringToIntArray() 함수 테스트
        ArrayList<Integer> list = convertStringToIntArray(input);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    static boolean hasCustomDelimiter(String input) { // 커스텀 구분자가 지정되어 있는지 확인하는 함수
        return input.startsWith("//");
    }

    static String getCustomDelimiter(String input) { // 문자열에서 커스텀 구분자를 추출하고 반환하는 함수
        int end_index = input.indexOf("\\n");
        return input.substring(2, end_index);
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
        if (hasCustomDelimiter(input)) { // 구분자가 있다면 인덱스를 구분자 다음으로 조정
            index = input.indexOf("\\n") + 2;
        }

        ArrayList<Integer> numbers = new ArrayList<>(); // 숫자 배열을 저장하는 ArrayList
        StringBuilder current_number = new StringBuilder(); // 현재 숫자를 임시로 저장하는 StringBuilder

        for (int i = index; i < input.length(); i++) {
            char current_char = input.charAt(i); // 현재 인덱스의 문자

            if (Character.isDigit(current_char)) { // 현재 문자가 숫자일 경우, current_number 에 추가
                current_number.append(current_char);
            } else if (isValidDelimiter(current_char) && !current_number.isEmpty()) { // 현재 문자가 구분자고, current_number 가 비어있지 않다면
                numbers.add(Integer.parseInt(current_number.toString()));
                current_number.setLength(0);
            } else { // 숫자도 구분자도 아니거나, 숫자 이전에 구분자가 나온 경우 잘못된 값이므로 IllegalArgumentException
                throw new IllegalArgumentException();
            }
        }

        // loop 종료 이후 버퍼에 남은 마지막 숫자 처리
        if (!current_number.isEmpty()) {
            numbers.add(Integer.parseInt(current_number.toString()));
        } else { // current_number 가 비어있다면, 구분자 이후에 숫자를 입력하지 않았으므로 IllegalArgumentException
            throw new IllegalArgumentException();
        }

        return numbers;
    }
}
