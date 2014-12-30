package backward;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class OsgiBeanReferenceFactoryBean implements SmartFactoryBean, BeanFactoryAware {

	private String targetBeanName;

	private BeanFactory beanFactory;


	/**
	 * Set the name of the target bean.
	 * <p>This property is required. The value for this property can be
	 * substituted through a placeholder, in combination with Spring's
	 * PropertyPlaceholderConfigurer.
	 * @param targetBeanName the name of the target bean
	 * @see PropertyPlaceholderConfigurer
	 */
	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		if (this.targetBeanName == null) {
			throw new IllegalArgumentException("'targetBeanName' is required");
		}
		if (!this.beanFactory.containsBean(this.targetBeanName)) {
			throw new NoSuchBeanDefinitionException(this.targetBeanName, this.beanFactory.toString());
		}
	}


	public Object getObject() throws BeansException {
		if (this.beanFactory == null) {
			throw new FactoryBeanNotInitializedException();
		}
		return this.beanFactory.getBean(this.targetBeanName);
	}

	public Class getObjectType() {
		if (this.beanFactory == null) {
			return null;
		}
		return this.beanFactory.getType(this.targetBeanName);
	}

	public boolean isSingleton() {
		if (this.beanFactory == null) {
			throw new FactoryBeanNotInitializedException();
		}
		return this.beanFactory.isSingleton(this.targetBeanName);
	}

	public boolean isPrototype() {
		if (this.beanFactory == null) {
			throw new FactoryBeanNotInitializedException();
		}
		return this.beanFactory.isPrototype(this.targetBeanName);
	}

	public boolean isEagerInit() {
		return false;
	}

}
