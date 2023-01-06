package com.amplet.io.apkg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApkgModel {
    
    private int id;
    private Map<Integer, ApkgTemplate> templates = new HashMap<Integer, ApkgTemplate>();
    private List<String> fields = new ArrayList<String>();
    
    public ApkgModel(int id, Map<Integer, ApkgTemplate> templates) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Map<Integer, ApkgTemplate> getTemplates() {
        return templates;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTemplates(Map<Integer, ApkgTemplate> templates) {
        this.templates = templates;
    }

    public void addTemplate(ApkgTemplate template) {
        this.templates.put(template.getOrd(), template);
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
