package geo.track.config;

import geo.track.annotation.ToRefactor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class DeletionLogger implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DeletionLogger.class);
    private final ApplicationContext applicationContext;

    public DeletionLogger(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Buscando métodos que estão marcados para refatoração @ToRefactor...");
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Class<?> beanClass = bean.getClass();

            // Handle proxied classes (e.g., AOP proxies)
            if (beanClass.getName().contains("$$EnhancerBySpringCGLIB$$") || beanClass.getName().contains("$$SpringCGLIB$$")) {
                beanClass = beanClass.getSuperclass();
            }

            for (Method method : beanClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(ToRefactor.class)) {
                    ToRefactor toRefactor = method.getAnnotation(ToRefactor.class);
                    log.warn("Method marked for future deletion/refactoring: {}.{}() - Reason: {}",
                            beanClass.getName(), method.getName(), toRefactor.reason());
                }
            }
        }
    }
}
