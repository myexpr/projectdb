package thoughtwok.projectdb;

import javax.servlet.annotation.WebServlet;

import org.vaadin.tokenfield.TokenField;

import thoughtwok.projectdb.dao.ProjectCollectionEnum;
import thoughtwok.projectdb.dao.ProjectDao;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
@Title("Project Database")
public class MyVaadinUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class,
            widgetset = "thoughtwok.projectdb.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        /*
         * final VerticalLayout layout = new VerticalLayout(); layout.setMargin(true); setContent(layout);
         * 
         * Button button = new Button("Click Me"); button.addClickListener(new Button.ClickListener() { public void
         * buttonClick(ClickEvent event) { layout.addComponent(new Label("Thank you for clicking")); } });
         * layout.addComponent(button);
         */

        final VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        FormLayout projectForm = new FormLayout();
        final PropertysetItem item = new PropertysetItem();
        item.addItemProperty(ProjectCollectionEnum._ID.name(), new ObjectProperty<String>("some id"));
        item.addItemProperty(ProjectCollectionEnum.COMMON_NAME.name(), new ObjectProperty<String>("hcom, hotels.com"));

        final FieldGroup projectFields = new FieldGroup(item);

        for (ProjectCollectionEnum i : new ProjectCollectionEnum[] {ProjectCollectionEnum._ID, ProjectCollectionEnum.COMMON_NAME}) {
            TextField textField = new TextField(i.name());
            projectForm.addComponent(textField);
            projectFields.bind(textField, i.name());
        }

        Button submitButton = new Button("Submit Project");
        projectForm.addComponent(submitButton);
        submitButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    projectFields.commit();
                    Notification.show("ok submitted the data with values " + item.getItemProperty("_ID") + " and name " + item.getItemProperty(ProjectCollectionEnum.COMMON_NAME.name()));
                    layout.addComponent(new Label(item.getItemProperty("_ID")));
                    //now invoke service layer
                    new ProjectDao().createProject(null);
                } catch (CommitException e) {
                    // TODO Auto-generated catch block
                    Notification.show("failed");
                }

            }
        });
        
        TokenField tf = new TokenField("For Tags");

        layout.addComponent(projectForm);

    }

}
