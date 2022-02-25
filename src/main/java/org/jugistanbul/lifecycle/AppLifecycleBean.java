package org.jugistanbul.lifecycle;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;
import org.jugistanbul.security.RequestMatcherRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 23.02.2022
 ***/
@ApplicationScoped
public class AppLifecycleBean
{
    @Inject
    Logger log;

    @Inject
    RequestMatcherRegistry requestMatcherRegistry;

    static final String[] EMPTY_ARRAY = new String[0];

    void onStart(@Observes StartupEvent ev) {
        log.info("The application is starting...");
        requestMatcherRegistry
                .addResource("/api/user/all", new String[]{"admin"}, false)
                .addResource("/user-list", new String[]{"admin"}, false)
                .addResource("/api/signIn", EMPTY_ARRAY, true)
                .addResource("/login", EMPTY_ARRAY, true)
                .addResource("/app/guest", new String[]{"admin", "guest"}, false);
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("The application is stopping...");
    }
}
