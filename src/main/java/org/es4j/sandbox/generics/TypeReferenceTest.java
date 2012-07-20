package org.es4j.sandbox.generics;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.util.*;


public class TypeReferenceTest {

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

        private Map<Type, Object> favorites =
                new HashMap<Type, Object>();

        public <T> void setFavorite(TypeReference<T> ref, T thing) {
            favorites.put(ref.getType(), thing);
        }

        public <T> T getFavorite(TypeReference<T> ref) {
            @SuppressWarnings("unchecked")
            T ret = (T) favorites.get(ref.getType());
            return ret;
        }
    }

    
    public static void main(String[] args) {

        TypeReference<String>        stringTypeRef   = new TypeReference<String>() {};
        TypeReference<Integer>       integerTypeRef  = new TypeReference<Integer>() {};
        TypeReference<List<Boolean>> listBoolTypeRef = new TypeReference<List<Boolean>>() {};

        Favorites f = new Favorites();
        f.setFavorite(stringTypeRef,   "Java");
        f.setFavorite(integerTypeRef,  42);
        f.setFavorite(listBoolTypeRef, Arrays.asList(true, true));

        String        s    = f.getFavorite(stringTypeRef);
        int           i    = f.getFavorite(integerTypeRef);
        List<Boolean> list = f.getFavorite(listBoolTypeRef);

        System.out.println(s);
        System.out.println(i);
        System.out.println(list);
    }
}
