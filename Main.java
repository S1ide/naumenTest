

public class Main {
    public static void main(String[] args) {
        RouteFinder routeFinder = new MyRouteFinder();
        char[][] chars = Utils.generateField(5);
        Utils.printField(chars);
        chars = routeFinder.findRoute(chars);
//        if (chars != null) {
            Utils.printField(chars);
//        } else System.out.println("Пути нет");
    }
}
