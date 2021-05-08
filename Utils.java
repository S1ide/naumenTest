import java.util.HashMap;

public class Utils {
    private static HashMap<Character, Coordinates> map = new HashMap<>();
    private static char start = '@';
    private static char finish = 'X';
    private static char block ='#';
    private static char road ='.';

    public static char[][] generateField(int count) {
        char[][] field = new char[count][count];
         //генерация координат старта и финиша
            int x = (int) (1 + Math.random() * (count - 1));
            int y = (int) (1 + Math.random() * (count - 1));
            map.put(start, new Coordinates(x, y));
            while (true) {
                int newX = (int) (1 + Math.random() * (count - 1));
                int newY = (int) (1 + Math.random() * (count - 1));
                if (newX != x && newY != y) {
                    map.put(finish, new Coordinates(newX, newY));
                    break;
                }
            }

            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    int a = (int) Math.round(Math.random());
                    if (a > 0) {
                        field[i][j] = road;
                    } else field[i][j] = block;
                }
            }
            field[map.get(start).X][map.get(start).Y] = start;
            field[map.get(finish).X][map.get(finish).Y] = finish;
        return field;
    }

    public static void printField(char[][] chars) {
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < chars.length; j++) {
                    System.out.print(chars[i][j]);
                }
                System.out.println();
            }
            System.out.println();
    }
}
