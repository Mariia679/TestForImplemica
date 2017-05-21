package com.implemica.second;

import java.io.*;
import java.util.*;

/**
 * http://algs4.cs.princeton.edu/44sp/
 */
public class Main {

    private File nameFileIn;
    private File nameFileOut;

    public void main(Main task, File fileOut) {
        try {
            task.loadFromFile();
            task.solution();
            task.writeToFile();
            task.readFromFile(fileOut);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public Main(File nameFileIn, File nameFileOut) {
        this.nameFileIn = nameFileIn;
        this.nameFileOut = nameFileOut;
    }

    private class Data {
        // the list cities
        private List<Node> cities = new ArrayList<>();
        // the list of neighbours of cities
        private List<Edge> edges = new ArrayList<>();
        // the number of rute to find
        private Map<Integer, Edge> rutes = new HashMap<>();
        // the list of rute
        private List<List<Node>> listPath = new ArrayList<>();

    }

    private List<Data> graphs = new ArrayList<>();

    private void loadFromFile() throws IOException {

        class LoadFile {

            private int line;                       // line in file
            private Scanner inRecord;               // pointer on record of test
            private final Data data = new Data();

            private LoadFile(Scanner in, int line) {
                this.inRecord = in;
                this.line = line;
            }

            private Data read() throws IOException {
                readCity();
                readRutes();
                return data;
            }

            void readCity() throws IOException {
                int numberOfCity; // the number of cities
                if (inRecord.hasNextInt()) {
                    numberOfCity = inRecord.nextInt();
                    line++;
                } else
                    throw new IOException("The format of file is wrong, in line " + line + " is expected to integer");
                // initialization of the cities inRecord objects of Node, the first city corresponds to object of Node (0)
                for (int city = 0; city < numberOfCity; city++)
                    data.cities.add(new Node(city));

                for (int city = 0; city < numberOfCity; city++) {
                    if (inRecord.hasNextLine()) {
                        data.cities.get(city).setName(inRecord.next());
                        line++;
                    } else
                        throw new IOException("The format of file is wrong, in line " + line + " is expected to line");
                    readEdges(city);
                }
            }

            void readEdges(int fromCity) throws IOException {
                int p;                          // the number of neighbours of city
                int toCity;
                int cost;
                if (inRecord.hasNextInt()) {
                    p = inRecord.nextInt();
                    line++;
                } else
                    throw new IOException("The format of file is wrong, in line " + line + " is expected to integer");

                for (int i = 0; i < p; i++) {
                    if (inRecord.hasNextInt()) toCity = inRecord.nextInt();
                    else
                        throw new IOException("The format of file is wrong, in line " + line + " is expected to integer");
                    if (inRecord.hasNextInt()) {
                        cost = inRecord.nextInt();
                        line++;
                    } else
                        throw new IOException("The format of file is wrong, in line " + line + " is expected to integer");

                    Edge edge = new Edge(data.cities.get(fromCity), data.cities.get(toCity - 1), cost);
                    data.edges.add(edge);
                }
            }

            void readRutes() throws IOException {
                int r; //  // the number of rute to find
                if (inRecord.hasNextInt()) {
                    r = inRecord.nextInt();
                    line++;
                } else
                    throw new IOException("The format of file is wrong, in line " + line + " is expected to integer");

                String strFrom;
                String strTo;
                for (int i = 0; i < r; i++) {
                    if (inRecord.hasNext()) strFrom = inRecord.next();
                    else
                        throw new IOException("The format of file is wrong, in line " + line + " is expected to city");

                    if (inRecord.hasNext()) {
                        strTo = inRecord.next();
                        line++;
                    } else
                        throw new IOException("The format of file is wrong, in line " + line + " is expected to city");

                    Node cityFrom = null;
                    Node cityTo = null;

                    for (int j = 0; j < data.cities.size(); j++) {
                        if ((cityFrom == null) && (data.cities.get(j).getName().
                                equals(strFrom))) {
                            cityFrom = data.cities.get(j);
                        }
                        if ((cityTo == null) && (data.cities.get(j).getName().
                                equals(strTo))) {
                            cityTo = data.cities.get(j);
                        }
                    }
                    Edge edge = new Edge(cityFrom, cityTo, -1);
                    data.rutes.put(i, edge);
                }
            }
        } // end of class LoadFile

        Scanner in = null;
        int s;
        try {
            in = new Scanner(nameFileIn);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        int line = 0;

        if (in.hasNextInt()) {
            s = in.nextInt();
            line++;
        } else {
            throw new IOException("The format of file is wrong, in line " + line + " is expected to integer");
        }
        for (int i = 0; i < s; i++) {
            LoadFile record = new LoadFile(in, line);
            graphs.add(record.read());
            in = record.inRecord;
            line = record.line;

            if (in.hasNextLine()) {
                in.nextLine();
                line++;
            }
        }
        in.close();
    }


    private void solution() {

        for (Data data : graphs) {  // for each graph to calculate the rute

            FloydWarshallAlgorithm floydeWarshall = new FloydWarshallAlgorithm(data.cities, data.edges);

            for (int i = 0; i < data.rutes.size(); i++) {
                floydeWarshall.getShortestDistance(data.rutes.get(i));
                data.listPath.add(floydeWarshall.getShortestPath(data.rutes.get(i)));
            }
        }
    }


    private void writeToFile() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(nameFileOut);
            for (Data data : graphs) {
                for (int i = 0; i < data.rutes.size(); i++) {
                    out.println(data.rutes.get(i).getWeight());
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

    private void readFromFile(File fileOut) throws IOException {
        StringBuilder sb = null;
        BufferedReader in;
        try {
            sb = new StringBuilder();
            if (!fileOut.exists()) {
                throw new FileNotFoundException(fileOut.getName());
            }
            in = new BufferedReader(new FileReader(fileOut.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(sb);
    }
}
