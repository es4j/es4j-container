package org.es4j.container;

import java.util.HashMap;
import java.util.Map;
import org.es4j.exceptions.ArgumentException;
import org.es4j.exceptions.ArgumentNullException;
import org.es4j.logging.api.ILog;
import org.es4j.logging.api.LogFactory;


public class NanoContainer {
    private static final ILog logger = LogFactory.buildLogger(NanoContainer.class);
    private final Map<Class, ContainerRegistration> registrations = new HashMap<Class, ContainerRegistration>();

    public <TService> ContainerRegistration register(Class clazz, Resolver resolver) {
        logger.debug(Messages.registeringWireupCallback(), clazz.getName());
        ContainerRegistration registration = new ContainerRegistration(resolver);
        this.registrations.put(clazz, registration);
        return registration;
    }

    public <TService> ContainerRegistration register(/*Class clazz,*/ TService instance) {
        if (instance == null)
            throw new ArgumentNullException("instance", Messages.instanceCannotBeNull());
        
        if(!instance.getClass().isPrimitive() && !instance.getClass().isInterface())
            throw new ArgumentException(Messages.typeMustBeInterface(), "instance");

        logger.debug(Messages.registeringServiceInstance(), instance.getClass());
        ContainerRegistration registration = new ContainerRegistration(instance);
        this.registrations.put(instance.getClass(), registration);
        return registration;
    }

    public <TService> TService resolve(Class<TService> clazz) {
        logger.debug(Messages.resolvingService(), clazz.getClass().getName());

        ContainerRegistration registration = this.registrations.containsKey(clazz)? this.registrations.get(clazz) 
                                                                                  : null;       
        if (registrations != null)
            return (TService)registration.resolve(this);

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
