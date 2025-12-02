package nl.rumfumme.verlet;

import java.util.HashSet;
import java.util.Set;

public class World {
    Set<Entity> entities = new HashSet<Entity>();

    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }

    public void addEntities(Set<Entity> entities) {
        this.entities.addAll(entities);
    }

    public Entity addEntity(Entity entity) {
        entities.add(entity);

        return entity;
    }

    public void update() {
        for (Entity e : entities) {
            e.update();
        }
    }

    public void draw() {
        for (Entity e : entities) {
            e.draw();
        }
    }
}
