package com.amplet.io.apkg;

import java.util.HashMap;
import java.util.Map;

public class ApkgModel {
    
    private int id;
    private Map<Integer, ApkgTemplate> templates = new HashMap<Integer, ApkgTemplate>();
    
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
}
