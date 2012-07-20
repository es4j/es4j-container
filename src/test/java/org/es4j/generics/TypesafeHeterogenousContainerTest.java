package org.es4j.generics;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TypesafeHeterogenousContainerTest {

    
    public static abstract class TypeReference<T> {

        private final Type type;

        protected TypeReference() {
            Type superclass = this.getClass().getGenericSuperclass();
            if (superclass instanceof Class<?>) {
                throw new RuntimeException("Missing type parameter.");
            }
            this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        }

        public Type getType() {
            return this.type;
        }
    }

    
    static class Favorites {

        private Map<Type, Object> favorites = new HashMap<Type, Object>();

        public <T> void setFavorite(TypeReference<T> ref, T thing) {
            favorites.put(ref.getType(), thing);
        }

        public <T> T getFavorite(TypeReference<T> ref) {
            @SuppressWarnings("unchecked")
            T ret = (T) favorites.get(ref.getType());
            return ret;
        }
    }

    
    @Test
    public void testFavorite() {

        TypeReference<String>        stringTypeRef   = new TypeReference<String>() {};
        TypeReference<Integer>       integerTypeRef  = new TypeReference<Integer>() {};
        TypeReference<List<Boolean>> listBoolTypeRef = new TypeReference<List<Boolean>>() {};

        Favorites f = new Favorites();
        f.setFavorite(stringTypeRef,   "Java");
        f.setFavorite(integerTypeRef,  42);
        f.setFavorite(listBoolTypeRef, Arrays.asList(true, true));

        assertEquals(f.getFavorite(stringTypeRef),  "Java");
        assertEquals(f.getFavorite(integerTypeRef), (Integer)42);
        assertEquals(f.getFavorite(listBoolTypeRef), Arrays.asList(true, true));
    }
}
