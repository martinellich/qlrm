package org.qlrm.mapper;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcResultMapper extends ResultMapper {

    @SuppressWarnings("unchecked")
    public <T> List<T> list(ResultSet rs, Class<T> clazz) throws SQLException {
        List<T> result = new ArrayList<T>();
        Constructor<?> ctor = (Constructor<?>) clazz.getDeclaredConstructors()[0];

        while (rs.next()) {
            Object[] objs = new Object[ctor.getParameterTypes().length];
            for (int i = 0; i < ctor.getParameterTypes().length; i++) {
                objs[i] = rs.getObject(i + 1);
            }
            result.add((T) createInstance(ctor, objs));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T uniqueResult(ResultSet rs, Class<T> clazz) throws SQLException {
        Constructor<T> ctor = (Constructor<T>) clazz.getDeclaredConstructors()[0];
        rs.next();
        Object[] objs = new Object[ctor.getParameterTypes().length];
        for (int i = 0; i < ctor.getParameterTypes().length; i++) {
            objs[i] = rs.getObject(i + 1);
        }
        return createInstance(ctor, objs);
    }
}
