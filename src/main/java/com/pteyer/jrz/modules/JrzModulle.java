package com.pteyer.jrz.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.pteyer.jrz.services.IGuidService;
import com.pteyer.jrz.services.impl.GuidServiceImpl;
import com.pteyer.jrz.services.impl.GuidServiceImpl;

public class JrzModulle extends AbstractModule {
    @Override
    protected void configure() {
        bind(IGuidService.class).to(GuidServiceImpl.class).in(Singleton.class);
    }
}
