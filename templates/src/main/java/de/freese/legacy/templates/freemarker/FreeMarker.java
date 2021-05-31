package de.freese.legacy.templates.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

/**
 * @author Thomas Freese
 */
public class FreeMarker
{
    /**
     * @return {@link List}
     */
    public static List<String> getNames()
    {
        List<String> list = new ArrayList<>();

        list.add("ArrayList element 1");
        list.add("ArrayList element 2");
        list.add("ArrayList element 3");
        list.add("ArrayList element 4");

        return list;
    }

    /**
     * @param args String[]
     */
    public static void main(final String[] args)
    {
        String templateFile = "example.ftl";

        try
        {

            Configuration cfg = new Configuration(new Version("2.3.31"));
            // cfg.setTemplateLoader(new FileTemplateLoader(new File(".")));
            // cfg.setTemplateLoader(new ClassTemplateLoader(FreeMarker.class, ""));
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates/freemarker"));
            cfg.setNumberFormat("0.#######");
            // cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
            // cfg.setObjectWrapper(BeansWrapper.getDefaultInstance());

            Template template = cfg.getTemplate(templateFile);

            Map<String, Object> model = new HashMap<>();
            // SimpleHash model = new SimpleHash();
            model.put("names", getNames());
            model.put("Math", Math.class);
            model.put("PI", Double.valueOf(Math.PI));

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out)))
            {
                template.process(model, writer);
                writer.flush();
            }
        }
        catch (Exception ex)
        {
            System.err.println(ex);
        }
    }
}
