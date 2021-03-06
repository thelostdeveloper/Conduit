package systems.conduit.main.core.datastore.backend;

import systems.conduit.main.core.datastore.DatastoreHandler;
import systems.conduit.main.core.datastore.Storable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Datastore backend that puts everything in memory.
 *
 * @author Innectic
 * @since 12/30/2019
 */
public class MemoryBackend implements DatastoreHandler {

    private Map<String, Object> storage = new HashMap<>();

    @Override
    public void attach(Map<String, Object> meta) {
        this.storage = new HashMap<>();
    }

    @Override
    public void detach() {
        this.storage = null;
    }

    @Override
    public void set(String key, String value) {
        this.storage.put(key, value);
    }

    @Override
    public void set(String key, int value) {
        this.storage.put(key, value);
    }

    @Override
    public void set(String key, float value) {
        this.storage.put(key, value);
    }

    @Override
    public void set(String key, double value) {
        this.storage.put(key, value);
    }

    @Override
    public void set(String key, Storable<?> value) {
        this.storage.put(key, value);
    }

    @Override
    public Optional<Integer> getInt(String key) {
        Object object = this.storage.get(key);
        if (object == null) return Optional.empty();

        try {
            return Optional.of((Integer) object);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Float> getFloat(String key) {
        Object object = this.storage.get(key);
        if (object == null) return Optional.empty();

        try {
            return Optional.of((Float) object);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Double> getDouble(String key) {
        Object object = this.storage.get(key);
        if (object == null) return Optional.empty();

        try {
            return Optional.of((Double) object);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getString(String key) {
        Object object = this.storage.get(key);
        if (object == null) return Optional.empty();

        try {
            return Optional.of((String) object);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    @Override
    public <T> Optional<Storable<T>> getCustom(String key) {
        Object object = this.storage.get(key);
        if (object == null) return Optional.empty();
        if (!(object instanceof Storable)) return Optional.empty();

        try {
            return Optional.of((Storable<T>) object);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(String key) {
        this.storage.remove(key);
    }
}
