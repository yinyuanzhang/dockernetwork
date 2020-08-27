import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDirectionDefault;
import org.gephi.io.importer.api.EdgeMergeStrategy;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.GraphDistance;
import org.openide.util.Lookup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GephiDemo3 {

    @SuppressWarnings("static-access")
    public static void main(String[] args) {

        /**
         *运行程序
         */

        List<Object> exportData = new ArrayList<Object>();
        exportData.add("image");
        exportData.add("diameter");
        exportData.add("pathlength");
        exportData.add("nodenumber");
        exportData.add("edgenumber");


        String path = "A:/pythonproject2/";
        //String fileName = "coreimages net structre";
        String fileName = "coreimages net structre undirected";

        List<List<Object>> datalist = new ArrayList<List<Object>>();
        datalist = new GephiDemo3().Run();

        File file = new GephiDemo3().createCSVFile(exportData, datalist, path, fileName);
        String fileName2 = file.getName();
        System.out.println("文件名称：" + fileName2);

    }

    public static File createCSVFile(List<Object> head, List<List<Object>> dataList,String outPutPath, String filename) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }




    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }





    private List<List<Object>> Run() {

        //Map<String,String[]> map = new HashMap<String,String[]>();
        List<List<Object>> datalist = new ArrayList<List<Object>>();
        File file = new File("A:\\pythonproject2\\subnet_coreimage");
        File[] subFile = file.listFiles();


        for (int i = 0; i < subFile.length; i++) {

            System.out.println("bbbbbbbbbbbbbb");
            System.out.println(i);
            String filename = subFile[i].getName().split("\\.csv")[0];


            ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
            pc.newProject();
            Workspace workspace1 = pc.getCurrentWorkspace();


            ImportController importController = Lookup.getDefault().lookup(ImportController.class);
            GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();


            Container container;       //, container2;
            try {
                File file_node = new File(getClass().getResource("subnet_coreimage/" + filename + ".csv").toURI());
                System.out.println(filename);
                container = importController.importFile(file_node);
                container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);   //Force DIRECTED
                container.getLoader().setAllowAutoNode(false);  //create missing nodes
                container.getLoader().setEdgesMergeStrategy(EdgeMergeStrategy.SUM);
                container.getLoader().setAutoScale(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                return datalist;
            }


            importController.process((Container) container, new DefaultProcessor(), workspace1);
            GraphDistance distance = new GraphDistance();
            //distance.setDirected(true);
            distance.setDirected(false);
            distance.execute(graphModel);
            DirectedGraph directedGraph = graphModel.getDirectedGraph();

            System.out.println(distance.getDiameter());
            System.out.println(distance.getPathLength());
            System.out.println("aaaaaaaa");
            System.out.println();

//            String[] attribute = new String[4];
//            attribute[0] = String.valueOf(distance.getDiameter());
//            attribute[1] = String.valueOf(distance.getPathLength());
//            attribute[2] = String.valueOf(directedGraph.getNodeCount());
//            attribute[3] = String.valueOf(directedGraph.getEdgeCount());
//
//            map.put(filename,attribute);

            //System.out.println(map);

            List<Object> data=new ArrayList<Object>();
            data.add(filename);
            data.add(distance.getDiameter());
            data.add(distance.getPathLength());
            data.add(directedGraph.getNodeCount());
            data.add(directedGraph.getEdgeCount());
            datalist.add(data);
        }


//        for (String key : map.keySet()) {
//            System.out.println("key = " + key);
//        }
//
//        for (String[] value : map.values()) {
//            System.out.println("value0 = " + value[0]);
//            System.out.println("value1 = " + value[1]);
//            System.out.println("value2 = " + value[2]);
//            System.out.println("value3 = " + value[3]);
//        }

            return datalist;
        }




}
