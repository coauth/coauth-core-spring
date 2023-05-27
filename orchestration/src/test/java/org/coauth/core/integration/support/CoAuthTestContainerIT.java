package org.coauth.core.integration.support;

import org.testcontainers.containers.MySQLContainer;

public class CoAuthTestContainerIT extends MySQLContainer<CoAuthTestContainerIT> {

  private static final String IMAGE_VERSION = "mysql:8.0.33-debian";

  private static CoAuthTestContainerIT container;

  private CoAuthTestContainerIT() {
    super(IMAGE_VERSION);
  }

  public static CoAuthTestContainerIT getInstance() {
    System.out.println("TEST CONTAINER LOADING");
    if (container == null) {
      container = new CoAuthTestContainerIT().withInitScript("scripts.sql");
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.out.println("TEST CONTAINER STARTING");
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
  }

  @Override
  public void stop() {
    //do nothing, JVM handles shut down
  }

}
