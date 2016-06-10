package org.rayn.jflask.framework.orm;

import com.avaje.ebean.EbeanServer;

/**
 * EbeanServerFactory
 * Created by Raynxxx on 2016/06/10.
 */
public interface EbeanServerFactory {

    EbeanServer getEbeanServer();
}
