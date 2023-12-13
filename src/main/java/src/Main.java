package src;

import java.util.ArrayList;

public class Main {
    static int widthInCell = 50;
    static int heightInCell = 50;
    ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

    public static void main(String[] args) {
        new App();
//        new Main().addVillage(null, null, null);
    }

    private Point getDeltaFromSpiralCheck(Point begin, Point end, ArrayList<ArrayList<Boolean>> matrixFreeCells) {
        // https://www.cyberforum.ru/csharp-beginners/thread1153172.html#post6068234
        int x = 0;
        int y = 0;
        int dx = 1;
        int dy = 0;
        int dirChanges = 0;
        int w = end.getX() - begin.getX();
        int h = end.getY() - begin.getY();
        int visits = w;

        for (int i = 0; i < w * h; i++) {
            if (!matrixFreeCells.get(y).get(x))
                return new Point(dx, dy);

            if (--visits == 0) {
                visits = w * (dirChanges % 2) + h * ((dirChanges + 1) % 2) - (dirChanges / 2 - 1) - 2;
                int temp = dx;
                dx = -dy;
                dy = temp;
                dirChanges++;
            }

            x += dx;
            y += dy;
        }

        return new Point(0, 0);
    }

    public void addVillage(Point begin, Point end, ArrayList<ArrayList<Boolean>> matrixFreeCells) {
        // todo место для реализации наполеонских затей
        // correcting points
        for (int i = 0; i < heightInCell; i++) {
            var buf = new ArrayList<Integer>();
            for (int j = 0; j < widthInCell; j++) {
                buf.add(0);
            }

            matrix.add(buf);
        }
        try {
            int counterBlocksHouse = 0;
            int w = end.getX() - begin.getX();
            int h = end.getY() - begin.getY();
            while (counterBlocksHouse < (w + h) / 3) {
                Point beginHouse = new Point((int) (w * Math.random()), (int) (h * Math.random()));
                Point endHouse = new Point((int) (beginHouse.getX() + (w / 3 + 1) * Math.random()),
                        (int) (beginHouse.getY() + (h / 3 + 1) * Math.random())); // todo take global size


            }

        } catch (Exception e) {
            System.out.println("gg");
        }

        for (int i = 0; i < heightInCell; i++) {
            for (int j = 0; j < widthInCell; j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}
