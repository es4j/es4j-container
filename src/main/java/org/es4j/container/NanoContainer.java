package org.es4j.container;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.es4j.util.logging.ILog;
import org.es4j.util.logging.LogFactory;


public class NanoContainer {
    private static final ILog logger = LogFactory.buildLogger(NanoContainer.class);
    private final Map<GenericType, ContainerRegistration> registrations = new HashMap<GenericType, ContainerRegistration>();

    public <TService> ContainerRegistration register(final TService instance) {
        return this.register(new Resolver<TService>() {

            @Override
            public TService resolve(NanoContainer container) {
                return instance;
            }
        });
    }
    
    public <TService> ContainerRegistration register(final Resolver<TService> resolver) {
        GenericType gtype = new GenericType();
        Type type = gtype.getType().getClass().getTypeParameters()[0];
        logger.debug(Messages.registeringWireupCallback(), type.getClass().getName());
        ContainerRegistration registration = new ContainerRegistration(resolver);
        //this.registrations.put(type, registration);
        return registration;
    }
/*
    public <TService> ContainerRegistration register(TService instance) {
        if (instance == null)
            throw new ArgumentNullException("instance", Messages.instanceCannotBeNull());
        
        if(!instance.getClass().isPrimitive() && !instance.getClass().isInterface())
            throw new ArgumentException(Messages.typeMustBeInterface(), "instance");

        logger.debug(Messages.registeringServiceInstance(), instance.getClass());
        ContainerRegistration registration = new ContainerRegistration(instance);
        this.registrations.put(new GenericType(instance.getClass()), registration);
        return registration;
    }
*/
    public <TService> TService resolve(Class<TService> clazz) {
        logger.debug(Messages.resolvingService(), clazz.getName());

        ContainerRegistration registration = this.registrations.containsKey(clazz)? this.registrations.get(clazz)
                                                                                  : null;       
        if (registrations != null)
            return (TService)registration.resolve(this);

        logger.debug(Messages.unableToResolve(), clazz.getClass().getName());
        return null; // default(TService);
    }
    
    public <TService> List<TService> resolveAll(Class<TService> clazz) {
        logger.debug(Messages.resolvingService(), clazz.getName());

        ContainerRegistration registration = this.registrations.containsKey(clazz)? this.registrations.get(clazz)
                                                                                  : null;       
        if (registrations != null) {
            return (List<TService>)registration.resolve(this);
        }

        logger.debug(Messages.unableToResolve(), clazz.getClass().getName());
        return null; // default(TService);
    }
}


	//using System;
	//using System.Collections.Generic;
	//using Logging;
/*
	public class NanoContainer
	{
		private static readonly ILog Logger = LogFactory.BuildLogger(typeof(NanoContainer));
		private readonly IDictionary<Type, ContainerRegistration> registrations =
			new Dictionary<Type, ContainerRegistration>();

		public virtual ContainerRegistration Register<TService>(Func<NanoContainer, TService> resolve)
			where TService : class
		{
			Logger.Debug(Messages.RegisteringWireupCallback, typeof(TService));
			var registration = new ContainerRegistration(c => (object)resolve(c));
			this.registrations[typeof(TService)] = registration;
			return registration;
		}

		public virtual ContainerRegistration Register<TService>(TService instance)
		{
			if (Equals(instance, null))
				throw new ArgumentNullException("instance", Messages.InstanceCannotBeNull);

			if (!typeof(TService).IsValueType && !typeof(TService).IsInterface)
				throw new ArgumentException(Messages.TypeMustBeInterface, "instance");

			Logger.Debug(Messages.RegisteringServiceInstance, typeof(TService));
			var registration = new ContainerRegistration(instance);
			this.registrations[typeof(TService)] = registration;
			return registration;
		}

		public virtual TService Resolve<TService>()
		{
			Logger.Debug(Messages.ResolvingService, typeof(TService));

			ContainerRegistration registration;
			if (this.registrations.TryGetValue(typeof(TService), out registration))
				return (TService)registration.Resolve(this);

			Logger.Debug(Messages.UnableToResolve, typeof(TService));
			return default(TService);
		}
	}
*/
