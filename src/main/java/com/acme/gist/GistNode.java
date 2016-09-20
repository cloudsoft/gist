package com.acme.gist;

import org.apache.brooklyn.api.effector.Effector;
import org.apache.brooklyn.api.entity.ImplementedBy;
import org.apache.brooklyn.config.ConfigKey;
import org.apache.brooklyn.core.config.ConfigKeys;
import org.apache.brooklyn.core.effector.Effectors;
import org.apache.brooklyn.entity.stock.BasicEntity;

@ImplementedBy(GistNodeImpl.class)
public interface GistNode extends BasicEntity {

    ConfigKey<String> OAUTH_KEY = ConfigKeys.newStringConfigKey("oauth.key", "OAuth key for creating a gist",
            "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");	

    Effector<Void> CREATE_GIST = Effectors.effector(Void.class, "createGist")
            .description("Create a Gist")
            .parameter(String.class, "gistName", "Gist Name", "Demo Gist")
            .parameter(String.class, "fileName", "File Name", "Hello.java")
            .parameter(String.class, "gistContents", "Gist Contents", "System.out.println(\"Hello World\");")
            .parameter(OAUTH_KEY)
            .buildAbstract();
}

