package com.amplet.io.apkg.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.amplet.io.apkg.ApkgModel;
import com.amplet.io.apkg.ApkgTemplate;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ModelDeserializer implements JsonDeserializer<List<ApkgModel>> {

    @Override
    public List<ApkgModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Map<String, JsonElement> jsonMap = jsonObject.asMap();
        Collection<JsonElement> models = jsonMap.values();
        List<ApkgModel> apkgModels = new ArrayList<ApkgModel>();
        models.forEach(model -> {
            JsonObject jsonObjectModel = model.getAsJsonObject();
            ApkgModel apkgModel = new ApkgModel(jsonObjectModel.getAsJsonObject().get("id").getAsInt());
            jsonObjectModel.getAsJsonArray("flds").forEach(field -> {
                apkgModel.addField(field.getAsJsonObject().get("name").getAsString());
            });
            jsonObjectModel.getAsJsonArray("tmpls").forEach(template -> {
                JsonObject jsonObjectTemplate = template.getAsJsonObject();
                apkgModel.addTemplate(new ApkgTemplate(jsonObjectTemplate.get("ord").getAsInt(),
                        jsonObjectTemplate.get("qfmt").getAsString(), jsonObjectTemplate.get("afmt").getAsString()));
            });
            apkgModels.add(apkgModel);
        });
        return apkgModels;
    }

}
