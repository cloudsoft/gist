package com.acme.gist;

import java.io.IOException;
import java.util.Collections;

import org.apache.brooklyn.core.effector.EffectorBody;
import org.apache.brooklyn.entity.stock.BasicEntityImpl;
import org.apache.brooklyn.util.core.config.ConfigBag;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GistNodeImpl extends BasicEntityImpl implements GistNode {

    private static final Logger LOG = LoggerFactory.getLogger(GistNodeImpl.class);

    @Override
    public void init() {
        super.init();
        getMutableEntityType().addEffector(CREATE_GIST, new EffectorBody<Void>() {
            @Override
            public Void call(ConfigBag parameters) {
                String gistName = String.valueOf(parameters.getStringKey("gistName"));
                String fileName = String.valueOf(parameters.getStringKey("fileName"));
                String gistContents = String.valueOf(parameters.getStringKey("gistContents"));
                String oathToken = parameters.get(OAUTH_KEY);

                GistFile file = new GistFile();
                file.setContent(gistContents);
                Gist gist = new Gist();
                gist.setDescription(gistName);
                gist.setFiles(Collections.singletonMap(fileName, file));
                gist.setPublic(true);
                GistService service = new GistService();
                service.getClient().setOAuth2Token(oathToken);
                LOG.info("Created Gist: " +  gistName);
                try {
                    service.createGist(gist);
                } catch (IOException e) {
                    LOG.error("Failed to create Gist", e);
                }
                return null;
            }
        });

    }
}

