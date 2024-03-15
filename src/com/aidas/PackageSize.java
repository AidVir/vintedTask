package com.aidas;

public enum PackageSize {
  S("S"),
  M("M"),
  L("L");

  public final String label;

  private PackageSize(String label) {
    this.label = label;
  }
}
