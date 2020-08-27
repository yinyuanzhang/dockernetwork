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

import java.io.File;

public class GephiDemo2 {

    public static void main(String[] args) {

        /**
         *运行程序
         */
        GephiDemo2 gephiDemo2 = new GephiDemo2();
        gephiDemo2.Run();
    }

    private void Run() {

        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace1 = pc.getCurrentWorkspace();


        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();

        Container container;       //, container2;
        try {
            File file_node = new File(getClass().getResource("dversion6/bash.csv").toURI());
            container = importController.importFile(file_node);
            container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);   //Force DIRECTED
            container.getLoader().setAllowAutoNode(false);  //create missing nodes
            container.getLoader().setEdgesMergeStrategy(EdgeMergeStrategy.SUM);
            container.getLoader().setAutoScale(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        importController.process((org.gephi.io.importer.api.Container) container, new DefaultProcessor(), workspace1);
        GraphDistance distance = new GraphDistance();
        distance.setDirected(true);
        distance.execute(graphModel);

        System.out.println(distance.getDiameter());
        System.out.println(distance.getPathLength());







        //ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        //pc.newProject();
        Workspace workspace2 = pc.getCurrentWorkspace();



        //ImportController importController2 = Lookup.getDefault().lookup(ImportController.class);
        //GraphModel graphModel2 = Lookup.getDefault().lookup(GraphController.class).getGraphModel();

        Container container2;       //, container2;
        try {
            File file_node2 = new File(getClass().getResource("dversion6/ubuntu.csv").toURI());
            container2 = importController.importFile(file_node2);
            container2.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);   //Force DIRECTED
            container2.getLoader().setAllowAutoNode(false);  //create missing nodes
            container2.getLoader().setEdgesMergeStrategy(EdgeMergeStrategy.SUM);
            container2.getLoader().setAutoScale(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }


        importController.process((org.gephi.io.importer.api.Container) container2, new DefaultProcessor(), workspace2);
        //GraphDistance distance2 = new GraphDistance();
        //distance2.setDirected(true);
        distance.execute(graphModel);
        System.out.println("aaaaaa");
        System.out.println(distance.getDiameter());
        System.out.println(distance.getPathLength());


    }
}
