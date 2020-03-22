package daos;

import java.util.List;

public interface Dao<T> {
    public T findById(int id);
    public List<T> findAll();
    public boolean update(T dto);
    public boolean create(T dto);
    public void delete(int id);

}
