package pl.pwlctk;

import javafx.scene.effect.BoxBlur;

public class Effects {

    //Effects

    public static void setBlur() {
        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(10);
        boxBlur.setHeight(3);
        boxBlur.setIterations(3);
        Main.getStage().getScene().getRoot().setEffect(boxBlur);
    }

    public static void setDefault() {
        Main.getStage().getScene().getRoot().setEffect(null);
    }
}
