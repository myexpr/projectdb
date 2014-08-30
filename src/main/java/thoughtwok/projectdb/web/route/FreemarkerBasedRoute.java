package thoughtwok.projectdb.web.route;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import spark.Request;
import spark.Response;
import spark.Route;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class FreemarkerBasedRoute extends Route {

    private static Configuration configuration;
    private Template template;

    static {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(FreemarkerBasedRoute.class, "/freemarker");
    }

    /**
     * Constructor
     * 
     * @param path The route path which is used for matching. (e.g. /hello, users/:name)
     */
    protected FreemarkerBasedRoute(final String path, final String templateName) throws IOException {
        super(path);
        template = FreemarkerBasedRoute.configuration.getTemplate(templateName);
    }

    @Override
    public Object handle(Request request, Response response) {
        StringWriter writer = new StringWriter();
        try {
            doHandle(request, response, writer);
        } catch (Exception e) {
            e.printStackTrace();
            response.redirect("/internal_error");
        }
        return writer;
    }

    protected abstract void doHandle(final Request request, final Response response, final Writer writer)
            throws IOException, TemplateException;
    
    protected Template getTemplate() {
        return this.template;
    }
    
    protected Template getTemplate(String templatePath) {
        Template template = null;
        try {
            template = FreemarkerBasedRoute.configuration.getTemplate(templatePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return template;
    }

}
