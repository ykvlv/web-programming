package beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.HitDataBase;
import hit.Hit;
import hit.HitCollection;
import hit.HitList;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Model
@ApplicationScoped
public class HitHistory implements Serializable, HitCollection {
    private String dbMessage = "Подключение к Базе Данных ⤓⤓⤓";
    private final HitList hits = new HitList();
    private String jsonHits = "";
    private final HitDataBase hitDataBase = new HitDataBase();

    public void dbConnect() {
        if (hitDataBase.restoreConnection()) {
            dbMessage = "Подключение к базе данных успешно";
        } else {
            dbMessage = "Не удается подключится к базе данных";
        }
    }

    @Override
    public boolean addHit(Hit hit) {
        return hits.addHit(hit);
    }

    @Override
    public boolean clearHits() {
        return hits.clearHits();
    }

    @Override
    public List<Hit> getHits() {
        return hits.getHits().stream().sorted((a, b) -> a.compareTo(b) * -1).collect(Collectors.toList());
    }

    public void appendToDb() {
        if (hitDataBase.restoreConnection() && hitDataBase.synchronizeProjection(hits)) {
            dbMessage = "Коллекция была сохранена";
        } else {
            dbMessage = "Не удается сохранить коллекцию";
        }
    }

    public void loadFromDb() {
        if (hitDataBase.restoreConnection() && hits.synchronizeOr(hitDataBase)) {
            dbMessage = "Коллекция была синхронизирована";
        } else {
            dbMessage = "Не удается синхронизировать коллекцию";
        }
    }

    public String getDbMessage() {
        return dbMessage;
    }

    public void setDbMessage(String dbMessage) {
        this.dbMessage = dbMessage;
    }

    public String getJsonHits() {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        jsonHits = gson.toJson(hits);
        return jsonHits;
    }

    public void setJsonHits(String jsonHits) {
        this.jsonHits = jsonHits;
    }
}
