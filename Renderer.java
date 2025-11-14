import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Renderer extends JPanel {

    private Point3D[] cube;
    private int[][] cubeEdges;
    private ArrayList<Point3D> houseVertices;
    private ArrayList<int[]> houseEdges;
    public Point3D P2;
    public Point3D P3;

    public Renderer() {
        loadCubeFromFile("cube.txt");
        houseVertices = new ArrayList<>();
        houseEdges = new ArrayList<>();
        loadHouseFromFile("house3d.txt");
        loadPointsFromFile("points.txt");
        setBackground(Color.WHITE);
    }

    private void loadCubeFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int numVertices = Integer.parseInt(br.readLine().trim());
            ArrayList<Point3D> vertices = new ArrayList<>();
            for (int i = 0; i < numVertices; i++) {
                String line = br.readLine().trim();
                if (line.contains("#")) {
                    line = line.substring(0, line.indexOf("#")).trim();
                }
                if (line.isEmpty()) continue;
                String[] coords = line.split("\\s+");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                double z = Double.parseDouble(coords[2]);
                vertices.add(new Point3D(x, y, z));
            }

            cube = vertices.toArray(new Point3D[0]);

            int numEdges = Integer.parseInt(br.readLine().trim());
            cubeEdges = new int[numEdges][];
            for (int i = 0; i < numEdges; i++) {
                String line = br.readLine().trim();
                if (line.contains("#")) {
                    line = line.substring(0, line.indexOf("#")).trim();
                }
                if (line.isEmpty()) continue;
                String[] indices = line.split("\\s+");
                int v1 = Integer.parseInt(indices[0]);
                int v2 = Integer.parseInt(indices[1]);
                cubeEdges[i] = new int[]{v1, v2};
            }
        } catch (IOException e) {
            System.err.println("Error al cargar cube.txt: " + e.getMessage());
            cube = new Point3D[] {
                new Point3D(0, 0, -200), new Point3D(100, 0, -200),
                new Point3D(100, 100, -200), new Point3D(0, 100, -200),
                new Point3D(0, 0, -300), new Point3D(100, 0, -300),
                new Point3D(100, 100, -300), new Point3D(0, 100, -300)
            };
            cubeEdges = new int[][] {
                {0,1},{1,2},{2,3},{3,0}, {4,5},{5,6},{6,7},{7,4}, {0,4},{1,5},{2,6},{3,7}
            };
        }
    }

    private void loadHouseFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int numVertices = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < numVertices; i++) {
                String line = br.readLine().trim();
                if (line.contains("#")) {
                    line = line.substring(0, line.indexOf("#")).trim();
                }
                if (line.isEmpty()) continue;
                String[] coords = line.split("\\s+");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                double z = Double.parseDouble(coords[2]);
                houseVertices.add(new Point3D(x, y, z - 150));
            }

            int numEdges = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < numEdges; i++) {
                String line = br.readLine().trim();
                if (line.contains("#")) {
                    line = line.substring(0, line.indexOf("#")).trim();
                }
                if (line.isEmpty()) continue;
                String[] indices = line.split("\\s+");
                int v1 = Integer.parseInt(indices[0]);
                int v2 = Integer.parseInt(indices[1]);
                houseEdges.add(new int[]{v1, v2});
            }
        } catch (IOException e) {
            System.err.println("Error al cargar house3d.txt: " + e.getMessage());
            houseVertices = new ArrayList<>();
            houseEdges = new ArrayList<>();
        }
    }

    private void loadPointsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int pointCount = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] coords = line.split("\\s+");
                if (coords.length >= 3) {
                    double x = Double.parseDouble(coords[0]);
                    double y = Double.parseDouble(coords[1]);
                    double z = Double.parseDouble(coords[2]);
                    if (pointCount == 0) {
                        P2 = new Point3D(x, y, z);
                    } else if (pointCount == 1) {
                        P3 = new Point3D(x, y, z);
                        break;
                    }
                    pointCount++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar points.txt: " + e.getMessage());
            P2 = new Point3D(0, 0, -100);
            P3 = new Point3D(50, 50, -199);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(100, 150, 255));
        g2.setStroke(new java.awt.BasicStroke(1.5f));
        for (int[] e : houseEdges) {
            if (e[0] < houseVertices.size() && e[1] < houseVertices.size()) {
                drawLine3D(g2, houseVertices.get(e[0]), houseVertices.get(e[1]), new Color(100, 150, 255));
            }
        }

        g2.setColor(Color.GREEN);
        g2.setStroke(new java.awt.BasicStroke(2.0f));
        for (int[] e : cubeEdges) {
            drawLine3D(g2, cube[e[0]], cube[e[1]], Color.GREEN);
        }

        Cohen3D.ClipResult res = Cohen3D.clipLine(P2, P3);

        g2.setStroke(new java.awt.BasicStroke(2.5f));
        if (res.trivialReject) {
            drawLine3D(g2, P2, P3, Color.BLUE);
        } else {
            drawLine3D(g2, res.a, res.b, Color.RED);
        }
    }

    private int[] project(Point3D p) {
        double t = -100.0 / p.z;
        double xp = t * p.x;
        double yp = t * p.y;

        int width = getWidth();
        int height = getHeight();
        int cx = width / 2;
        int cy = height / 2;

        double scale = 4.0;

        int sx = (int) (cx + xp * scale);
        int sy = (int) (cy - yp * scale);

        return new int[] { sx, sy };
    }

    private void drawLine3D(Graphics2D g2, Point3D a, Point3D b, Color c) {
        int[] A = project(a);
        int[] B = project(b);
        g2.setColor(c);
        g2.drawLine(A[0], A[1], B[0], B[1]);
    }
}
