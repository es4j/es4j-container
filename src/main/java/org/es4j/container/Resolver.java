package org.es4j.container;

public abstract class Resolver<TService> {

    abstract public TService resolve(NanoContainer container);
    
}
