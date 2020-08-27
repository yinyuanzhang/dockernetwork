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
import org.gephi.statistics.plugin.PageRank;
import org.openide.util.Lookup;

import java.io.File;


/**
 *
 * @author Qi
 */
public class GephiDemo {

    public static void main(String[] args) {

        /**
         *运行程序
         */
        GephiDemo gephiDemo = new GephiDemo();
        gephiDemo.Run();
    }



    public void Run(){
        //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();


        //Get controllers and models
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        //Get models and controllers for this new workspace - will be useful later
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();


        //Import file
        Container container;       //, container2;
        try {
            //File file_node = new File(getClass().getResource("remove_owner.csv").toURI());
            File file_node = new File(getClass().getResource("dockerfile_prase_notage.csv").toURI());
            container = importController.importFile(file_node);
            container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);   //Force DIRECTED
            container.getLoader().setAllowAutoNode(false);  //create missing nodes
            container.getLoader().setEdgesMergeStrategy(EdgeMergeStrategy.SUM);
            container.getLoader().setAutoScale(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }



        //Append imported data to GraphAPI
        importController.process((org.gephi.io.importer.api.Container) container, new DefaultProcessor(), workspace);
        //importController.process((org.gephi.io.importer.api.Container) container2, new AppendProcessor(), workspace); //Use AppendProcessor to append to current workspace


        //See if graph is well imported
        //DirectedGraph graph = graphModel.getDirectedGraph();
        //UndirectedGraph undirectedGraph = graphModel.getUndirectedGraphVisible();
        //System.out.println(undirectedGraph.getNodeCount());


        GraphDistance distance = new GraphDistance();
        distance.setDirected(true);
        distance.execute(graphModel);
        //System.out.println(distance.getReport());


        //System.out.println(distance.getDiameter());
        //System.out.println(distance.getPathLength());


        //System.out.println("Nodes: " + graph.getNodeCount());
        //System.out.println("Edges: " + graph.getEdgeCount());

        PageRank pageRank = new PageRank();
        pageRank.setDirected(true);
        pageRank.execute(graphModel);
        //pageRank.getReport();
        System.out.println(pageRank.getReport());
        System.out.println(pageRank.getEpsilon());
    }

}



