package eg.edu.alexu.csd.datastructure.iceHockey.Classes;
import eg.edu.alexu.csd.datastructure.iceHockey.Interfaces.IPlayersFinder;
import java.util.*;
import java.awt.*;

public class Main implements IPlayersFinder {

    private boolean[][] visited;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private int area;
    private int team;
    private String[] photo;



    @Override
    public Point[] findPlayers(String[] photo, int team, int threshold) {

        if ( photo == null || photo.length == 0)
            return null;

        if (photo[0].length() == 0)
            return null;

        this.photo = photo;
        this.team = team;

        final int maxDim = 100;
        this.visited = new boolean[maxDim][maxDim];

        for (int i = 0; i < maxDim-1; i++) {
            Arrays.fill(visited[i], false);
        }

        ArrayList<Point> points = new ArrayList<Point>();
        for (int i = 0; i < photo.length; i++) {
            for (int j = 0; j < photo[i].length(); j++) {
                if (this.visited[i][j] == false) {
                    this.area = 0; this.xMin = maxDim; this.yMin = maxDim;
                    this.xMax = -1; this.yMax =-1;

                    this.floodfill(i, j);

                    //System.out.printf("%d %d %d %d %d\n" ,x_min,y_min,x_max,y_max, this.area);
                    if(this.area >= threshold){
                        int x = (this.xMin*2 + (this.xMax + 1)*2 ) / 2;
                        int y = (this.yMin*2 + (this.yMax + 1)*2 ) / 2;
                        points.add(new Point(y, x));
                    }

                }
            }
        }
        Point[] arr = new Point[points.size()];
        for(int i = 0; i < points.size(); i++){
            arr[i] = points.get(i);
        }
        Arrays.sort(arr, new PointCmp());
        return arr;
    }
    // minx, miny, max-x, max-y
    public void floodfill(int i, int j) {

        if (i < 0 || j < 0 || i >= photo.length || j >= photo[0].length())
            return ;


        if (this.photo[i].charAt(j) - '0' != this.team || this.visited[i][j] == true)
            return;

        this.visited[i][j] = true;
        int boxArea = 4;
        this.area += boxArea;

        this.xMin = Math.min(xMin, i);
        this.xMax = Math.max(xMax, i);

        this.yMin = Math.min(yMin, j);
        this.yMax = Math.max(yMax, j);

        this.floodfill(i + 1, j);
        this.floodfill(i, j + 1);
        this.floodfill(i - 1, j);
        this.floodfill(i, j - 1);


    }

}

class PointCmp implements Comparator<Point> {
    public int compare(final Point a, final Point b) {
      return (a.x < b.x) ? -1 : (a.x > b.x) ? 1 : 0;
    }
}