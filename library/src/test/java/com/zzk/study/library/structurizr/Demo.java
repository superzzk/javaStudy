package com.zzk.study.library.structurizr;

import com.structurizr.Workspace;
import com.structurizr.documentation.Format;
import com.structurizr.documentation.template.StructurizrDocumentationTemplate;
import com.structurizr.io.plantuml.C4PlantUMLWriter;
import com.structurizr.io.plantuml.PlantUMLWriter;
import com.structurizr.model.*;
import com.structurizr.view.*;
import org.junit.Test;

public class Demo {

    @Test
    public void getting_started(){
        Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
        Model model = workspace.getModel();

        Person user = model.addPerson("User", "A user of my software system.");
        SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");
        user.uses(softwareSystem, "Uses");

        // create some views
        ViewSet views = workspace.getViews();
        SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "An example of a System Context diagram.");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        // add some documentation
        StructurizrDocumentationTemplate template = new StructurizrDocumentationTemplate(workspace);
        template.addContextSection(Format.Markdown,
                "Here is some context about the software system...\n" +
                        "\n" +
                        "![](embed:SystemContext)");

        // add some colour and shapes
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        System.out.println(Utils.getPlantUmlStr(views.getSystemContextViews().stream().findFirst().get()));
    }
    @Test
    public void systemContextView() {
        Workspace ws = getDemoWorkspace();
        final SystemContextView systemContextView = ws.getViews().getSystemContextViews().stream().findFirst().get();

        // export to c4 plant uml
        PlantUMLWriter plantUMLWriter = new C4PlantUMLWriter();
        final String c4String = plantUMLWriter.toString(systemContextView);
        System.out.println(c4String);

//        StringWriter stringWriter = new StringWriter();
//        plantUMLWriter.write(ws, stringWriter);
//        System.out.println(stringWriter.toString());
    }

    @Test
    public void containerView(){
        Workspace ws = getDemoWorkspace();
        final ContainerView containerView = ws.getViews().getContainerViews().stream().findFirst().get();
        System.out.println(Utils.getC4StringForXuecheng(containerView));
    }
    @Test
    public void componentView(){
        Workspace ws = getDemoWorkspace();
        final ComponentView componentView = ws.getViews().getComponentViews().stream().findFirst().get();
        System.out.println(Utils.getC4StringForXuecheng(componentView));
    }


    private Workspace getDemoWorkspace(){
        // create a workspace and a model
        Workspace workspace = new Workspace("Payment Gateway", "Payment Gateway");
        Model model = workspace.getModel();

        // define a user and two systems
        Person user = model.addPerson("Merchant", "Merchant");
        SoftwareSystem paymentTerminal = model.addSoftwareSystem("Payment Terminal", "Payment Terminal");
        user.uses(paymentTerminal, "Makes payment");
        SoftwareSystem fraudDetector = model.addSoftwareSystem("Fraud Detector", "Fraud Detector");
        paymentTerminal.uses(fraudDetector, "Obtains fraud score");

        // container
        Container f5 = paymentTerminal.addContainer(
                "Payment Load Balancer", "Payment Load Balancer", "F5");
        Container jvm1 = paymentTerminal.addContainer(
                "JVM-1", "JVM-1", "Java Virtual Machine");
        Container jvm2 = paymentTerminal.addContainer(
                "JVM-2", "JVM-2", "Java Virtual Machine");
        Container jvm3 = paymentTerminal.addContainer(
                "JVM-3", "JVM-3", "Java Virtual Machine");
        Container oracle = paymentTerminal.addContainer(
                "oracleDB", "Oracle Database", "RDBMS");

        f5.uses(jvm1, "route");
        f5.uses(jvm2, "route");
        f5.uses(jvm3, "route");

        jvm1.uses(oracle, "storage");
        jvm2.uses(oracle, "storage");
        jvm3.uses(oracle, "storage");


        // component
        Component jaxrs = jvm1.addComponent("jaxrs-jersey",
                "restful webservice implementation", "rest");
        Component gemfire = jvm1.addComponent("gemfire",
                "Clustered Cache Gemfire", "cache");
        Component hibernate = jvm1.addComponent("hibernate",
                "Data Access Layer", "jpa");
        jaxrs.uses(gemfire, "");
        gemfire.uses(hibernate, "");

        // create a view
        ViewSet viewSet = workspace.getViews();

        SystemContextView contextView = viewSet.createSystemContextView(paymentTerminal, "context", "Payment Gateway Diagram");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        ContainerView view = workspace.getViews().createContainerView(paymentTerminal, "F5", "Container View");
        view.addAllContainers();

        ComponentView componentView = workspace.getViews()
                .createComponentView(jvm1, "JVM_COMPOSITION", "JVM Components");
        componentView.addAllComponents();

        return workspace;
    }
}
