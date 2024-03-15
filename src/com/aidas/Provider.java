package com.aidas;

public enum Provider {
  LP("LP"),
  MR("MR");

  public final String label;

  private Provider(String label) {
    this.label = label;
  }
}
