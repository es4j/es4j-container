package org.es4j.container;

import java.lang.reflect.Type;
import org.es4j.logging.api.ILog;
import org.es4j.logging.api.LogFactory;


public class ContainerRegistration<TService> {
    private static final ILog logger = LogFactory.buildLogger(ContainerRegistration.class);
    private final Resolver<TService> resolver;
    private final Type               type;
    private TService                 instance;
    private boolean                  instancePerCall;

    public ContainerRegistration(Resolver<TService> resolver) {
        logger.verbose(Messages.addingWireupCallback());
        this.instance = null;
        this.resolver = resolver;
        this.type     = GenericType.getTypeArgument(getClass(), GenericEntity.class);
    }
    
    public ContainerRegistration(TService instance) {
        logger.verbose(Messages.addingWireupRegistration(), instance.getClass());
        this.instance = instance;
        this.resolver = null;
        this.type     = GenericType.getTypeArgument(getClass(), GenericEntity.class);
    }

    public ContainerRegistration instancePerCall() {
        logger.verbose(Messages.configuringInstancePerCall());
        this.instancePerCall = true;
        return this;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public TService resolve(NanoContainer container) {
        logger.verbose(Messages.resolvingInstance());
        
        if (this.instancePerCall) {
            logger.verbose(Messages.buildingNewInstance());
            return this.resolver.resolve(container);
        }

        logger.verbose(Messages.attemptingToResolveInstance());
        
        if (this.instance != null)
            return this.instance;

        logger.verbose(Messages.buildingAndStoringNewInstance());
        return this.instance = this.resolver.resolve(container);
    }    
}


/*
	public class ContainerRegistration
	{
		private static readonly ILog Logger = LogFactory.BuildLogger(typeof(ContainerRegistration));
		private readonly Func<NanoContainer, object> resolve;
		private object instance;
		private bool instancePerCall;

		public ContainerRegistration(Func<NanoContainer, object> resolve)
		{
			Logger.Verbose(Messages.AddingWireupCallback);
			this.resolve = resolve;
		}
		public ContainerRegistration(object instance)
		{
			Logger.Verbose(Messages.AddingWireupRegistration, instance.GetType());
			this.instance = instance;
		}

		public virtual ContainerRegistration InstancePerCall()
		{
			Logger.Verbose(Messages.ConfiguringInstancePerCall);
			this.instancePerCall = true;
			return this;
		}
		public virtual object Resolve(NanoContainer container)
		{
			Logger.Verbose(Messages.ResolvingInstance);
			if (this.instancePerCall)
			{
				Logger.Verbose(Messages.BuildingNewInstance);
				return this.resolve(container);
			}

			Logger.Verbose(Messages.AttemptingToResolveInstance);

			if (this.instance != null)
				return this.instance;

			Logger.Verbose(Messages.BuildingAndStoringNewInstance);
			return this.instance = this.resolve(container);
		}
	}
*/