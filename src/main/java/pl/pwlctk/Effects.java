package pl.pwlctk;

import javafx.scene.effect.BoxBlur;

class Effects {

    static void setBlur() {
        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(10);
        boxBlur.setHeight(3);
        boxBlur.setIterations(3);
        Main.getStage().getScene().getRoot().setEffect(boxBlur);
    }

    static void setDefault() {
        Main.getStage().getScene().getRoot().setEffect(null);
    }
}
