import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Cohen3D {

    private static final int LEFT   = 1;
    private static final int RIGHT  = 2;
    private static final int BELOW  = 4;
    private static final int ABOVE  = 8;
    private static final int FARZ   = 16;
    private static final int NEARZ  = 32;

    private static double XMIN = 0.0;
    private static double XMAX = 100.0;
    private static double YMIN = 0.0;
    private static double YMAX = 100.0;
    private static double ZMIN = -300.0;
    private static double ZMAX = -200.0;

    static {
        loadClippingVolume("clipping_volume.txt");
    }

    private static void loadClippingVolume(String filename) {
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
                        XMIN = x;
                        YMIN = y;
                        ZMAX = z;
                    } else if (pointCount == 1) {
                        XMAX = x;
                        YMAX = y;
                        ZMIN = z;
                        break;
                    }
                    pointCount++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar clipping_volume.txt, usando valores por defecto: " + e.getMessage());
            XMIN = 0.0;
            XMAX = 100.0;
            YMIN = 0.0;
            YMAX = 100.0;
            ZMIN = -300.0;
            ZMAX = -200.0;
        }
    }

    public static class ClipResult {
        public boolean trivialReject;
        public Point3D a;
        public Point3D b;

        public ClipResult(boolean trivialReject, Point3D a, Point3D b) {
            this.trivialReject = trivialReject;
            this.a = a;
            this.b = b;
        }
    }

    private static int computeOutCode3D(Point3D p) {
        int code = 0;

        if (p.x < XMIN) code |= LEFT;
        if (p.x > XMAX) code |= RIGHT;
        if (p.y < YMIN) code |= BELOW;
        if (p.y > YMAX) code |= ABOVE;
        if (p.z < ZMIN) code |= FARZ;
        if (p.z > ZMAX) code |= NEARZ;

        return code;
    }

    public static ClipResult clipLine(Point3D p1, Point3D p2) {
        Point3D A = new Point3D(p1);
        Point3D B = new Point3D(p2);

        int codeA = computeOutCode3D(A);
        int codeB = computeOutCode3D(B);

        int maxIterations = 10;
        int iterations = 0;

        while (iterations < maxIterations) {
            iterations++;
            
            if ((codeA | codeB) == 0) {
                return new ClipResult(false, A, B);
            }

            if ((codeA & codeB) != 0) {
                return new ClipResult(true, null, null);
            }

            int outCodeOut = (codeA != 0) ? codeA : codeB;
            Point3D P = (codeA != 0) ? A : B;

            double dx = B.x - A.x;
            double dy = B.y - A.y;
            double dz = B.z - A.z;

            double x = P.x;
            double y = P.y;
            double z = P.z;

            if ((outCodeOut & LEFT) != 0 && Math.abs(dx) > 1e-10) {
                double t = (XMIN - A.x) / dx;
                if (t > 0 && t < 1) {
                    x = XMIN;
                    y = A.y + t * dy;
                    z = A.z + t * dz;
                }
            } else if ((outCodeOut & RIGHT) != 0 && Math.abs(dx) > 1e-10) {
                double t = (XMAX - A.x) / dx;
                if (t > 0 && t < 1) {
                    x = XMAX;
                    y = A.y + t * dy;
                    z = A.z + t * dz;
                }
            } else if ((outCodeOut & BELOW) != 0 && Math.abs(dy) > 1e-10) {
                double t = (YMIN - A.y) / dy;
                if (t > 0 && t < 1) {
                    x = A.x + t * dx;
                    y = YMIN;
                    z = A.z + t * dz;
                }
            } else if ((outCodeOut & ABOVE) != 0 && Math.abs(dy) > 1e-10) {
                double t = (YMAX - A.y) / dy;
                if (t > 0 && t < 1) {
                    x = A.x + t * dx;
                    y = YMAX;
                    z = A.z + t * dz;
                }
            } else if ((outCodeOut & FARZ) != 0 && Math.abs(dz) > 1e-10) {
                double t = (ZMIN - A.z) / dz;
                if (t > 0 && t < 1) {
                    x = A.x + t * dx;
                    y = A.y + t * dy;
                    z = ZMIN;
                }
            } else if ((outCodeOut & NEARZ) != 0 && Math.abs(dz) > 1e-10) {
                double t = (ZMAX - A.z) / dz;
                if (t > 0 && t < 1) {
                    x = A.x + t * dx;
                    y = A.y + t * dy;
                    z = ZMAX;
                }
            } else {
                return new ClipResult(true, null, null);
            }

            if (outCodeOut == codeA) {
                A = new Point3D(x, y, z);
                codeA = computeOutCode3D(A);
            } else {
                B = new Point3D(x, y, z);
                codeB = computeOutCode3D(B);
            }
        }
        
        return new ClipResult(false, A, B);
    }
}
